package com.example.dao

import com.example.model.message.ChatMessage
import com.example.model.message.ChatRoom

interface DAOFacade {
    suspend fun allChatMessages(): List<ChatMessage>
    suspend fun chatMessage(id: String): ChatMessage?
    suspend fun addNewChatMessage(chatMessage: ChatMessage): ChatMessage?
    suspend fun editChatMessage(chatMessage: ChatMessage): Boolean
    suspend fun deleteChatMessage(id: String): Boolean
    suspend fun deleteAllChatMessage(): Boolean
    suspend fun allChatRooms(): List<ChatRoom>
    suspend fun addNewChatRoom(chatRoom: ChatRoom): ChatRoom?
    suspend fun allChatMessagesByChatId(chatId: String): List<ChatMessage>
}
