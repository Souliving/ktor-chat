package com.example.websockets

import com.example.dao.dao
import com.example.model.connection.Connection
import com.example.model.message.ChatMessage
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


fun Routing.oneChat() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket {
        println("Adding user!")
        val thisConnection = Connection(this)
        connections += thisConnection
        try {
//                send("You are connected! There are ${connections.count()} users here.")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                println(receivedText)
                val parsed: ChatMessage = Json.decodeFromString<ChatMessage>(receivedText)
                val sendMessage = parsed
                dao.addNewChatMessage(sendMessage)
                connections.forEach {
                    it.session.send(Json.encodeToString<ChatMessage>(sendMessage))
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            println(thisConnection.name)
            println("Removing $thisConnection!")
            connections -= thisConnection
        }
    }
}
