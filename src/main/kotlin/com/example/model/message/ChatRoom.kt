package com.example.model.message

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


data class ChatRoom(
    var id: String? = null,
    var chatId: String,
    var senderId: String,
    var recipientId: String
)

object ChatRooms: Table() {
    val id = varchar("id", 64)
    val chatId = varchar("chatId", 64)
    val senderId = varchar("senderId", 64)
    val recipientId = varchar("recipientId", 64)
}
