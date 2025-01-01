package com.security.app.controllers

import com.security.app.entities.Media
import com.security.app.model.MediaType
import com.security.app.services.MediaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/media")
class MediaController(private val mediaService: MediaService) {

    @GetMapping("/{mediaId}")
    fun getMediaById(@PathVariable mediaId: String) : ResponseEntity<Media> {
        val media = mediaService.getMediaById(mediaId)

        return if (media != null) {
            ResponseEntity.ok(media)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/url/{mediaUrl}")
    fun getMediaByUrl(@PathVariable mediaUrl: String) : ResponseEntity<Media> {
        val media = mediaService.getMediaByUrl(mediaUrl)

        return if (media != null) {
            ResponseEntity.ok(media)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/type/{mediaType}")
    fun getMediaByType(@PathVariable mediaType: String) : ResponseEntity<List<Media>> {
        val mediaTypeEnum = MediaType.fromStringValue(mediaType)
        val media = mediaService.getMediaByType(mediaTypeEnum)

        return if (media.isNotEmpty()) {
            ResponseEntity.ok(media)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}