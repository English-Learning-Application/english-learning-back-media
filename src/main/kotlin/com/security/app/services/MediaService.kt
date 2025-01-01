package com.security.app.services

import com.security.app.entities.Media
import com.security.app.model.MediaType
import com.security.app.repositories.MediaRepository
import org.springframework.stereotype.Service

@Service
class MediaService(private val mediaRepository: MediaRepository) {
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