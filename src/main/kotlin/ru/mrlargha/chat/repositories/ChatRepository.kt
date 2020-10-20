package ru.mrlargha.chat.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import ru.mrlargha.chat.entities.ChatInfo

interface ChatRepository : PagingAndSortingRepository<ChatInfo, Long> {

}