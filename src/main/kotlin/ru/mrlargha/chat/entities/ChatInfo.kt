package ru.mrlargha.chat.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "chats")
class ChatInfo(
        val chatName: String,
        val chatIconName: String?,

        @JsonIgnoreProperties("chats")
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "chats_users",
                joinColumns = [
                        JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
                ],
                inverseJoinColumns = [
                        JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false, updatable = false)])
        val users: MutableSet<User> = mutableSetOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
        val messages: MutableSet<ChatMessage> = mutableSetOf()
) : BaseEntity<Long>()