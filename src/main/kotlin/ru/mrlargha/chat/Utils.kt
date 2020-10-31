package ru.mrlargha.chat

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import java.util.*

object Utils {
    fun extractToken(headers: HttpHeaders): String? = headers["Authorization"]?.first()

    fun getUUID() = UUID.randomUUID().toString().replace("-", "")
}