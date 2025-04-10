package com.security.app.services

import com.security.app.entities.Media
import com.security.app.model.MediaType
import com.security.app.repositories.MediaRepository
import com.security.app.utils.toUUID
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.UUID


@Service
class MediaService(private val mediaRepository: MediaRepository, private val awsStorageService: AwsStorageService) {

    private val bucketName: String? = System.getenv("AWS_BUCKET_NAME")

    @Transactional
    fun uploadMedia(file: MultipartFile, mediaTypeStr: String): Media? {
        if (file.isEmpty) {
            return null
        }

        val fileName: String = StringUtils.cleanPath(file.originalFilename!!)
        val contentType = file.contentType
        val fileSize = file.size
        val inputStream = file.inputStream

       val url =  awsStorageService.uploadFile(bucketName, fileName, fileSize, contentType, inputStream)

        val media = Media().let {
            it.mediaUrl = url
            it.mediaType = MediaType.fromStringValue(mediaTypeStr)
            it
        }

        val result = mediaRepository.save(media)

        return result
    }

    @Transactional
    fun updateMedia(mediaId: String, file: MultipartFile, mediaTypeStr: String): Media? {
        var media: Media? = null
        if(mediaId.isNotEmpty()) {
            media = mediaRepository.findByMediaId(UUID.fromString(mediaId))?: return null
        }

        media?.let {
            awsStorageService.deleteFile(bucketName, it.mediaUrl)
        }

        return uploadMedia(file, mediaTypeStr)
    }

    fun getMediaById(mediaId: UUID): Media? {
        return mediaRepository.findByMediaId(mediaId)
    }

    fun getMediaByUrl(mediaUrl: String): Media? {
        return mediaRepository.findByMediaUrl(mediaUrl)
    }

    fun getMediaByType(mediaType: MediaType): List<Media> {
        return mediaRepository.findByMediaType(mediaType)
    }

    fun getMediaByQuery(query: List<String>): List<Media> {
        return mediaRepository.findAllByMediaIdIn(query.map { it.toUUID() })
    }

    @Transactional
    fun deleteMediaById(mediaId: UUID) {
        val media = mediaRepository.findByMediaId(mediaId)
        media?.let {
            awsStorageService.deleteFile(bucketName, it.mediaUrl)
            mediaRepository.deleteByMediaId(it.mediaId)
        }
    }
}