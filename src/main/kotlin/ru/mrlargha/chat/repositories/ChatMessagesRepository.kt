package ru.mrlargha.chat.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.mrlargha.chat.entities.ChatInfo
import ru.mrlargha.chat.entities.ChatMessage

@RepositoryRestResource(collectionResourceRel = "chats2", path = "chats2")
interface ChatMessagesRepository : PagingAndSortingRepository<ChatMessage, Long> {
    fun findAllByChat(chat: ChatInfo): List<ChatMessage>
}