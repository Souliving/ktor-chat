package com.example.model.message


import com.example.model.NoArg
import com.example.serialize.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime


@NoArg
@Serializable
data class ChatMessage(
    var id: String? = null,
    var chatId: String,
    var senderId: String,
    var recipientId: String,
    var senderName: String,
    var recipientName: String,
    var content: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    var sentAt: LocalDateTime,
    var status: String
)

object ChatMessages : Table() {
    val id = varchar("id", 64)
    val chatId = varchar("chatId", 64)
    val senderId = varchar("senderId", 64)
    val recipientId = varchar("recipientId", 64)
    val senderName = varchar("senderName", 64)
    val recipientName = varchar("recipientName", 64)
    val content = varchar("content", 64)
    val sentAt = datetime("sentAt")
    val status = varchar("status", 64)
}
