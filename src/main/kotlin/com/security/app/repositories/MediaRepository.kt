package com.security.app.repositories

import com.security.app.entities.Media
import com.security.app.model.MediaType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MediaRepository : JpaRepository<Media, String> {
    fun findByMediaId(mediaId: UUID): Media?
    fun findByMediaUrl(mediaUrl: String): Media?
    fun findByMediaType(mediaType: MediaType): List<Media>
    fun deleteByMediaId(mediaId: UUID)
}