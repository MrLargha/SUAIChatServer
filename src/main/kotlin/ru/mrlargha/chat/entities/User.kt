package ru.mrlargha.chat.entities

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        val firstName: String,
        val lastName: String,
        val email: String,
        val passHash: String,
        val token: String
) : BaseEntity<Long>() {
}