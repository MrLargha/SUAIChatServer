package ru.mrlargha.chat.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.mrlargha.chat.entities.User

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
interface UserRepository : PagingAndSortingRepository<User, Long> {
    fun findByEmail(@Param("email") name: String?): List<User>
    fun findByToken(@Param("token") name: String?): List<User>
    fun existsByToken(@Param("token") name: String?) : Boolean
}