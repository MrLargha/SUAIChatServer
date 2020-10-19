package ru.mrlargha.chat

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mrlargha.chat.entities.User
import ru.mrlargha.chat.repositories.UserRepository
import java.util.*
import java.util.concurrent.atomic.AtomicLong

@RestController()
class GreetingController(private val repository: UserRepository) {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

    @GetMapping("/register")
    fun registerUser(@RequestParam(value = "firstName") firstName: String, @RequestParam(value = "secondName") secondName: String): User {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        return User(firstName, secondName, "fds", "fdas", "fdsa").also { repository.save(it) }
    }

}