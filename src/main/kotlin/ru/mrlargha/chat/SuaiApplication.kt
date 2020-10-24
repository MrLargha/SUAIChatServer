package ru.mrlargha.chat

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import ru.mrlargha.chat.entities.ChatInfo
import ru.mrlargha.chat.entities.User
import ru.mrlargha.chat.repositories.ChatInfoRepository
import ru.mrlargha.chat.repositories.UserRepository

@SpringBootApplication


class SuaiApplication {
    @Bean
    fun mappingDemo(chatInfoRepository: ChatInfoRepository, userRepository: UserRepository)
    = CommandLineRunner{
        val user1 = User("Иванов", "Иван", "ivanov@mail.com", "pass")
        val user2 = User("Петров", "Сергей", "petrov@mail.com", "pass2")

        val chat1 = ChatInfo("Chat1", "icon1.png")
        val chat2 = ChatInfo("Chat2", "icon2.png")

        user1.chats.addAll(setOf(chat1, chat2))
        user2.chats.addAll(setOf(chat2))

        chatInfoRepository.saveAll(arrayListOf(chat1, chat2))
        userRepository.saveAll(arrayListOf(user1, user2))
    }
}

fun main(args: Array<String>) {
    runApplication<SuaiApplication>(*args)
}
