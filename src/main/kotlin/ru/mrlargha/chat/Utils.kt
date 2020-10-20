package ru.mrlargha.chat

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity

object Utils {
    fun extractToken(headers: HttpHeaders): String? = headers["Authorization"]?.first()
}