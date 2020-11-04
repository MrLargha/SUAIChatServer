package ru.mrlargha.chat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.mrlargha.chat.repositories.ChatInfoRepository
import ru.mrlargha.chat.repositories.UserRepository
import ru.mrlargha.chat.storage.FileSystemStorageService
import ru.mrlargha.chat.storage.StorageProperties
import ru.mrlargha.chat.storage.StorageService

@RequestMapping("/profile")
@Controller
class UserProfileController @Autowired constructor(
        private val userRepository: UserRepository,
        private val chatInfoRepository: ChatInfoRepository,
        private val storageService: StorageService
) {
    @PostMapping("/uploadProfileImage")
    fun uploadProfileImage(@RequestHeader headers: HttpHeaders, @RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val users = userRepository.findByToken(token)
        val user = if (users.isNotEmpty()) users.first() else return ResponseEntity(HttpStatus.UNAUTHORIZED)
        userRepository.save(user.apply { avatarName = file.originalFilename })
        storageService.store(file)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/getInfoAboutMe")
    fun getInfoAboutMe(@RequestHeader headers: HttpHeaders): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val users = userRepository.findByToken(token)
        val user = if (users.isNotEmpty()) users.first() else return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @PutMapping("/updateInfoAboutMe")
    fun updateInfo(@RequestHeader headers: HttpHeaders, @RequestBody body: ProfileUpdate): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val users = userRepository.findByToken(token)
        val user = if (users.isNotEmpty()) users.first() else return ResponseEntity(HttpStatus.UNAUTHORIZED)
        userRepository.save(user.apply {
            firstName = body.firstName
            lastName = body.lastName
        })
        return ResponseEntity.ok().build()
    }

    @PostMapping("/uploadChatImage")
    fun uploadChatImage(@RequestHeader headers: HttpHeaders, @RequestParam("file") file: MultipartFile,
                        @RequestParam chatId: Long
    ): ResponseEntity<Any> {
        val token = Utils.extractToken(headers) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        val users = userRepository.findByToken(token)
        if (users.isEmpty()) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val chat = chatInfoRepository.findById(chatId).get()
        chatInfoRepository.save(chat.apply { chatIconName = file.originalFilename })
        storageService.store(file)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/getImage/{filename:.+}")
    @ResponseBody
    fun getImage(@PathVariable filename: String): ResponseEntity<Any> {
        storageService.loadAsResource(filename)?.let {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${it.filename}").body(it)
        }
        return ResponseEntity.notFound().build()
    }

    data class ProfileUpdate(val firstName: String, val lastName: String)
}
