package com.security.app.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.stereotype.Service
import java.io.InputStream


@Service
class AwsStorageService(private val awsS3Client: AmazonS3) {
    fun uploadFile(
        bucketName: String?,
        keyName: String?,
        contentLength: Long?,
        contentType: String?,
        value: InputStream?
    ) : String {
        val metadata = ObjectMetadata()
        metadata.contentLength = contentLength!!
        metadata.contentType = contentType

        awsS3Client.putObject(bucketName, keyName, value, metadata)
        val url = awsS3Client.getUrl(bucketName, keyName)
        return url.path
    }


    fun deleteFile(
        bucketName: String?,
        keyName: String?
    ) {
        awsS3Client.deleteObject(bucketName, keyName)
    }
}