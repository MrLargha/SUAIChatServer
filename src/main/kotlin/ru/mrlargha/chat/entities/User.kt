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

        @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
        @JsonIgnore
        var chats: MutableSet<ChatInfo> = mutableSetOf(),

        var avatarName: String? = null
) : BaseEntity<Long>()