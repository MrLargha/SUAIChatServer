package ru.mrlargha.chat

import jdk.internal.loader.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mrlargha.chat.repositories.UserRepository

@RequestMapping("/profile")
@Controller
class ProfileController(
        private val userRepository: UserRepository,
        private val storageService: StorageService
) {
    @PostMapping("/uploadImage")
    fun uploadProfileImage(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        storageService.store(file)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/getImage/{filename:.+}")
    @ResponseBody
    fun getImage(@PathVariable filename: String): ResponseEntity<Any> =
            storageService.loadAsResource(filename)?.let {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${it.name}").body(it)
            } ?: ResponseEntity.notFound().build()

}