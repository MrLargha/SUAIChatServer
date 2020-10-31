package ru.mrlargha.chat.storage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream

@Service
class FileSystemStorageService @Autowired constructor(properties: StorageProperties) : StorageService {
    private val rootLocation: Path = Paths.get(properties.location)

    override fun store(file: MultipartFile?) {
        try {
            if (file!!.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }
            val destinationFile = rootLocation.resolve(
                    Paths.get(Objects.requireNonNull(file.originalFilename)))
                    .normalize().toAbsolutePath()
            if (destinationFile.parent != rootLocation.toAbsolutePath()) {
                // This is a security check
                throw StorageException(
                        "Cannot store file outside current directory.")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    override fun loadAll(): Stream<Path?>? {
        return try {
            Files.walk(rootLocation, 1)
                    .filter { path: Path -> path != rootLocation }
                    .map { other: Path? -> rootLocation.relativize(other) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String?): Path? {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String?): Resource? {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file!!.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                        "Could not read file: $filename")
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

}