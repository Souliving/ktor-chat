package com.example.dao

import com.example.database.DatabaseFactory.dbQuery
import com.example.model.message.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToChatMessage(row: ResultRow) = ChatMessage(
        id = row[ChatMessages.id],
        chatId = row[ChatMessages.chatId],
        senderId = row[ChatMessages.chatId],
        recipientId = row[ChatMessages.recipientId],
        senderName = row[ChatMessages.senderName],
        recipientName = row[ChatMessages.recipientName],
        content = row[ChatMessages.content],
        sentAt = row[ChatMessages.sentAt],
        status = row[ChatMessages.status]
    )

    private fun resultRowToChatRoom(row: ResultRow) = ChatRoom(
        id = row[ChatRooms.id],
        chatId = row[ChatRooms.chatId],
        senderId = row[ChatRooms.chatId],
        recipientId = row[ChatRooms.recipientId]
    )

    override suspend fun allChatMessages(): List<ChatMessage> = dbQuery {
        transaction {
            ChatMessages.selectAll().map(::resultRowToChatMessage)
        }
    }

    override suspend fun chatMessage(id: String): ChatMessage? = dbQuery {
        transaction {
            ChatMessages
                .select(ChatMessages.chatId eq id)
                .map(::resultRowToChatMessage)
                .singleOrNull()
        }
    }

    override suspend fun addNewChatMessage(chatMessage: ChatMessage): ChatMessage? = dbQuery {
        transaction {
            val insertStatement = ChatMessages.insert {
                it[ChatMessages.id] = chatMessage.id!!
                it[ChatMessages.sentAt] = chatMessage.sentAt
                it[ChatMessages.status] = chatMessage.status
                it[ChatMessages.chatId] = chatMessage.chatId
                it[ChatMessages.senderId] = chatMessage.senderId
                it[ChatMessages.content] = chatMessage.content
                it[ChatMessages.recipientId] = chatMessage.recipientId
                it[ChatMessages.senderName] = chatMessage.senderName
                it[ChatMessages.status] = chatMessage.status
                it[ChatMessages.recipientName] = chatMessage.recipientName
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToChatMessage)
        }
    }

    override suspend fun editChatMessage(chatMessage: ChatMessage): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChatMessage(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllChatMessage(): Boolean {
        var res = 0
        transaction {
            res = ChatMessages.deleteAll()
        }
        return res != 0
    }

    override suspend fun allChatRooms(): List<ChatRoom> = dbQuery {
        transaction {
            ChatRooms.selectAll().map(::resultRowToChatRoom)
        }
    }

    override suspend fun addNewChatRoom(chatRoom: ChatRoom): ChatRoom? = dbQuery {
        transaction {
            val insertStatement = ChatRooms.insert {
                it[ChatRooms.id] = chatRoom.id!!
                it[ChatRooms.chatId] = chatRoom.chatId
                it[ChatRooms.senderId] = chatRoom.senderId
                it[ChatRooms.recipientId] = chatRoom.recipientId
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToChatRoom)
        }
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allChatMessages().isEmpty()) {
            val chat =
                ChatMessage("1", "1", "1", "1", "1", "1", "1", LocalDateTime.now(), MessageStatuses.RECEIVED.toString())
            addNewChatMessage(chat)
        }
    }
}
