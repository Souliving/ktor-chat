package com.example.model.message

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class MessageStatuses {
    RECEIVED, DELIVERED
}

class PGEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}

object MessageStatusesTable : Table() {
    val messageStatusesColumn = customEnumeration(
        "messageStatusesColumn",
        "MessageStatusesTable",
        { value -> MessageStatuses.valueOf(value as String) },
        { PGEnum("MessageStatuses", it) })
}
