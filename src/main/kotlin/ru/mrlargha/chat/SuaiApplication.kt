package ru.mrlargha.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication


class SuaiApplication

fun main(args: Array<String>) {
    runApplication<SuaiApplication>(*args)
}
