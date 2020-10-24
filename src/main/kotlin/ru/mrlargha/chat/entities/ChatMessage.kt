package ru.mrlargha.chat.entities

import java.util.*
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "messages")
class ChatMessage(
        val chatId: Long,
        val userId: Long,
        val content: String,
        @Temporal(TemporalType.TIMESTAMP)
        val timestamp: Date
) : BaseEntity<Long>()