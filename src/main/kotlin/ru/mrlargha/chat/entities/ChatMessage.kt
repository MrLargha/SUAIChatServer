package ru.mrlargha.chat.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "messages")
class ChatMessage(
        @ManyToOne
        @JoinColumn(name = "chat", nullable = false)
        val chat: ChatInfo,
        val userId: Long,
        val content: String,
        @Temporal(TemporalType.TIMESTAMP)
        val date: Date
) : BaseEntity<Long>()