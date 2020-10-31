package ru.mrlargha.chat

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.mrlargha.chat.entities.User
import ru.mrlargha.chat.repositories.UserRepository
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern


@RestController
@RequestMapping("/auth")
class RestAuthController(private val userRepository: UserRepository) {

    data class RegisterPayload(
            val firstName: String,
            val lastName: String,
            val email: String,
            val password: String
    )

    data class LoginPayload(
            val email: String,
            val password: String
    )

    data class AuthResult(
            val state: String,
            val token: String? = null
    )

    companion object {
        private fun hashPassword(name: String, pass: String): String {
            val digest: MessageDigest = MessageDigest.getInstance("SHA3-256")
            return digest.digest((name + "_SALT_" + pass + "_SALT2_").toByteArray(StandardCharsets.UTF_8)).toString(StandardCharsets.UTF_8)
        }
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody requestBody: RegisterPayload): ResponseEntity<AuthResult> {
        val registeredUsers = userRepository.findByEmail(requestBody.email)
        if (registeredUsers.isNotEmpty()) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        val emailPattern = Pattern.compile("([a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*)@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+")
        if(!emailPattern.matcher(requestBody.email).matches() || requestBody.password.length < 5){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val encodedPassword = hashPassword(requestBody.email, requestBody.password)
        val token = Utils.getUUID()
        userRepository.save(User(requestBody.firstName, requestBody.lastName, requestBody.email, encodedPassword, token))
        return ResponseEntity(AuthResult("ok", token), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody requestBody: LoginPayload): ResponseEntity<AuthResult> {
        val users = userRepository.findByEmail(requestBody.email)
        val encodedPassword = hashPassword(requestBody.email, requestBody.password)
        if (users.isEmpty() || users.size > 1 || users.first().passHash != encodedPassword) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        val newToken = Utils.getUUID()
        users.first().let {
            userRepository.save(it.apply { this.token = newToken })
        }
        return ResponseEntity(AuthResult("ok", newToken), HttpStatus.OK)
    }

    @PostMapping("/logout")
    fun logoutUser(@RequestHeader headers: HttpHeaders): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val users = userRepository.findByToken(token)
        val user = if(users.isNotEmpty()) users.first() else return ResponseEntity(HttpStatus.UNAUTHORIZED)
        userRepository.save(user.apply { this.token = null })
        return ResponseEntity(HttpStatus.OK)
    }
}