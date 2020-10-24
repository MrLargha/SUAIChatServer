package ru.mrlargha.chat.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "chats")
class ChatInfo(
        val chatName: String,
        val chatIconName: String,
        @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
        @JsonIgnoreProperties("chats")
        val users: MutableSet<User> = mutableSetOf()
) : BaseEntity<Long>()