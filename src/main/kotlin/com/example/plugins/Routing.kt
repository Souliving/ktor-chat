package com.example.plugins

import com.example.dao.dao
import com.example.model.message.ChatMessage
import com.example.model.message.ChatRoom
import com.example.model.message.MessageStatuses
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import kotlin.time.measureTime

fun Application.configureRouting() {
    routing {
        get {
            call.respondText("Hello")
        }
//        get {
//            call.respondText("HTTP version is ${call.request.httpVersion}")
//            call.respond(HttpStatusCode.OK)
//        }

        get("/addChatMessage") {
            val chat =
                ChatMessage("1", "1", "1", "1", "1", "1", "1", LocalDateTime.now(), MessageStatuses.RECEIVED.toString())
            call.respondText(dao.addNewChatMessage(chat).toString())
        }

        get("/allChatMessages") {
            println(
                measureTime {
                    call.respondText(Json.encodeToString(dao.allChatMessages()))
                }
            )
        }

        get("/allChatMessages/{chatId}") {
            println(
                measureTime {
                    call.respondText(Json.encodeToString(dao.allChatMessagesByChatId(call.parameters["chatId"]!!)))
                }
            )

        }

        get("/addChatRoom") {
            val room = ChatRoom("1", "1", "1", "1")
            call.respondText(dao.addNewChatRoom(room)!!.toString())
        }

        get("/allChatRooms") {
            var all = ""
            dao.allChatRooms().forEach {
                all += it.toString()
                all += "\n"
            }
            call.respondText(all)
        }

        get("/deleteAllChatMessages") {
            call.respond(dao.deleteAllChatMessage())
        }
    }
}

