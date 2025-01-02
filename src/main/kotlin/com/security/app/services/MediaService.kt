package com.security.app.services

import com.security.app.entities.Media
import com.security.app.model.MediaType
import com.security.app.repositories.MediaRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MediaService(private val mediaRepository: MediaRepository, private val gcpStorageService: GcpStorageService) {

    fun uploadMedia(file: MultipartFile, mediaTypeStr: String): Media {
        val mediaType = MediaType.fromStringValue(mediaTypeStr)
        val mediaUrl = gcpStorageService.uploadFile(file, mediaType.getStorageFolder())

        val media = Media().let {
            it.mediaUrl = mediaUrl
            it.mediaType = mediaType
            it
        }

        val result = mediaRepository.save(media)

        return result
    }

    fun getMediaById(mediaId: String): Media? {
        return mediaRepository.findByMediaId(mediaId)
    }

    fun getMediaByUrl(mediaUrl: String): Media? {
        return mediaRepository.findByMediaUrl(mediaUrl)
    }

    fun getMediaByType(mediaType: MediaType): List<Media> {
        return mediaRepository.findByMediaType(mediaType)
    }

    fun deleteMediaById(mediaId: String) {
        mediaRepository.deleteByMediaId(mediaId)
    }
}