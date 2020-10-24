package ru.mrlargha.chat.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        val firstName: String,
        val lastName: String,
        @JsonIgnore
        val email: String,
        @JsonIgnore
        val passHash: String,
        @JsonIgnore
        var token: String? = null,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "chats_users",
                joinColumns = [
                    JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false, updatable = false)
                ],
                inverseJoinColumns = [
                    JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)])
        @JsonIgnoreProperties("users")
        var chats: MutableSet<ChatInfo> = mutableSetOf()
) : BaseEntity<Long>() {
}