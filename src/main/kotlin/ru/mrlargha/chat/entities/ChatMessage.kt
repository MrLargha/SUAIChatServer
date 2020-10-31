package ru.mrlargha.chat.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "messages")
class ChatMessage(
        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "chat", nullable = false)
        val chat: ChatInfo,

        @ManyToOne
        @JoinColumn(name = "user", nullable = false)
        val user: User,
        val content: String,
        @Temporal(TemporalType.TIMESTAMP)
        val date: Date
) : BaseEntity<Long>()