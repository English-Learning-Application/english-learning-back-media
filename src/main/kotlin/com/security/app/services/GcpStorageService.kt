package com.security.app.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class GcpStorageService {
    private val gcpServiceKeyJson = System.getenv()["GCP_SERVICE_KEY"] ?: throw Exception("GCP_SERVICE_KEY not found")
    private val credentials = GoogleCredentials.fromStream(gcpServiceKeyJson.byteInputStream())
    private val storage : Storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
    private val storageName = System.getenv()["GCP_STORAGE_NAME"] ?: "default"

    fun uploadFile(file: MultipartFile, folderName: String? = null): String {
        val fileName = "${folderName?.let { "$it/" } ?: ""}${file.originalFilename}"
        val blobId = BlobId.of(storageName, fileName)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.contentType).build()

        storage.create(blobInfo, file.bytes)

        return "https://storage.googleapis.com/$storageName/$fileName"
    }
}