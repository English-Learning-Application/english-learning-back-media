package com.security.app.controllers

import com.security.app.entities.Media
import com.security.app.model.ListMessage
import com.security.app.model.MediaType
import com.security.app.model.Message
import com.security.app.services.MediaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

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

    @PutMapping("/{mediaId}")
    fun updateMedia(
        @PathVariable mediaId: String,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("mediaType") mediaTypeStr: String
    ) : ResponseEntity<Message<Any?>> {
        val media = mediaService.updateMedia(mediaId, file, mediaTypeStr)
        return if(media != null) {
            ResponseEntity.ok(Message.Success("Media updated successfully", media))
        } else {
            ResponseEntity.badRequest().body(Message.Error("Failed to update media", 400))
        }
    }

    @DeleteMapping("/{mediaId}")
    fun deleteMediaById(@PathVariable mediaId: String) : ResponseEntity<Message<Any?>> {
        mediaService.deleteMediaById(UUID.fromString(mediaId))
        return ResponseEntity.ok(Message.Success("Media deleted successfully", null))
    }

    @GetMapping("/{mediaId}")
    fun getMediaById(@PathVariable mediaId: String) : ResponseEntity<Message<Any?>> {
        val mediaItem = mediaService.getMediaById(UUID.fromString(mediaId))

        if(mediaItem != null) {
            return ResponseEntity.ok(Message.Success("Media found", mediaItem))
        } else {
            return ResponseEntity.badRequest().body(Message.NotFound("Media not found"))
        }
    }

    @GetMapping("/query")
    fun getMediaByQuery(
        @RequestParam("q") query: List<String>
    ) : ResponseEntity<ListMessage<Any?>> {
        val media = mediaService.getMediaByQuery(query)

        return if (media.isNotEmpty()) {
            ResponseEntity.ok(ListMessage.Success("Media found", media))
        } else {
            ResponseEntity.badRequest().body(ListMessage.NotFound("Media not found"))
        }
    }

    @GetMapping("/url/{mediaUrl}")
    fun getMediaByUrl(@PathVariable mediaUrl: String) : ResponseEntity<Message<Any?>> {
        val media = mediaService.getMediaByUrl(mediaUrl)

        return if (media != null) {
            ResponseEntity.ok(Message.Success("Media found", media))
        } else {
            ResponseEntity.badRequest().body(Message.NotFound("Media not found"))
        }
    }

    @GetMapping("/type/{mediaType}")
    fun getMediaByType(@PathVariable mediaType: String) : ResponseEntity<Message<Any?>> {
        val mediaTypeEnum = MediaType.fromStringValue(mediaType)
        val media = mediaService.getMediaByType(mediaTypeEnum)

        return if (media.isNotEmpty()) {
            ResponseEntity.ok(Message.Success("Media found", media))
        } else {
            ResponseEntity.badRequest().body(Message.NotFound("Media not found"))
        }
    }
}