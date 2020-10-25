package ru.mrlargha.chat.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import ru.mrlargha.chat.entities.ChatInfo
import ru.mrlargha.chat.entities.User

interface ChatInfoRepository : PagingAndSortingRepository<ChatInfo, Long> {
    fun findAllByUsers(user: User): List<ChatInfo>
}