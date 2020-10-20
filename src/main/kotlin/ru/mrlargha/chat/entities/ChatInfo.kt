package ru.mrlargha.chat.entities

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "chat_info")
class ChatInfo : BaseEntity<Long>() {
}