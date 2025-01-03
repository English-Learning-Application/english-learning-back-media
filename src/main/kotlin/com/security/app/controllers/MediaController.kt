package com.security.app.controllers

import com.security.app.entities.Media
import com.security.app.model.MediaType
import com.security.app.model.Message
import com.security.app.services.MediaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/media")
class MediaController(private val mediaService: MediaService) {

    @PostMapping("/upload")
    fun uploadMedia(@RequestParam("file") file: MultipartFile, @RequestParam("mediaType") mediaTypeStr: String) : ResponseEntity<Message<Any>> {
        val media = mediaService.uploadMedia(file, mediaTypeStr)
        if(media == null) {
            return ResponseEntity.badRequest().body(Message.Error("Failed to upload media", 400))
        }
        return ResponseEntity.ok(Message.Success("Media uploaded successfully", media))
    }

    @DeleteMapping("/{mediaId}")
    fun deleteMediaById(@PathVariable mediaId: String) : ResponseEntity<Message<Any?>> {
        mediaService.deleteMediaById(mediaId)
        return ResponseEntity.ok(Message.Success("Media deleted successfully", null))
    }

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