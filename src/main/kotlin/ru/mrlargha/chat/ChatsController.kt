package ru.mrlargha.chat

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
//import ru.mrlargha.chat.repositories.ChatRepository
import ru.mrlargha.chat.repositories.UserRepository

@RequestMapping("/chats")
@RestController
class ChatsController(private val userRepository: UserRepository) {

    class UserPresentation(
            val firstName: String,
            val lastName: String,
            val id: Long
    )

    @GetMapping("/allusers")
    fun getAllUsers(@RequestHeader headers: HttpHeaders): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (!userRepository.existsByToken(token)) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return ResponseEntity(userRepository.findAll().map {
            UserPresentation(it.firstName, it.lastName, it.id ?: -1)
        }, HttpStatus.OK)
    }

}