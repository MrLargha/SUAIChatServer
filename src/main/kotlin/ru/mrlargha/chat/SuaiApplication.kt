package ru.mrlargha.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.mrlargha.chat.storage.StorageProperties

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties::class)
class SuaiApplication

fun main(args: Array<String>) {
    runApplication<SuaiApplication>(*args)
}

