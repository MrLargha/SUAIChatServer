package ru.mrlargha.chat

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.mrlargha.chat.entities.ChatInfo
import ru.mrlargha.chat.entities.User
import ru.mrlargha.chat.repositories.ChatInfoRepository
import ru.mrlargha.chat.repositories.ChatMessagesRepository
//import ru.mrlargha.chat.repositories.ChatRepository
import ru.mrlargha.chat.repositories.UserRepository

@RequestMapping("/chats")
@RestController
class ChatsController(
        private val userRepository: UserRepository,
        private val chatInfoRepository: ChatInfoRepository,
        private val chatMessagesRepository: ChatMessagesRepository
) {

    class UserPresentation(
            val firstName: String,
            val lastName: String,
            val id: Long
    )

    @GetMapping("/allusers")
    fun getAllUsers(@RequestHeader headers: HttpHeaders): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!userRepository.existsByToken(token)) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return ResponseEntity(userRepository.findAll(), HttpStatus.OK)
    }

    @PostMapping("/createChat")
    fun createChat(@RequestHeader headers: HttpHeaders, @RequestBody body: ChatCreateBody): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!userRepository.existsByToken(token)) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val users = userRepository.findAllById(body.usersIds)
        val chatInfo = chatInfoRepository.save(ChatInfo(body.chatName, null, users.toMutableSet()))
        return ResponseEntity(chatInfo, HttpStatus.OK)
    }

    data class ChatCreateBody(val chatName: String, val usersIds: List<Long>)
}