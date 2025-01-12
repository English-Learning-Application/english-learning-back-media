package com.security.app.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.security.app.utils.EncryptionUtils
import org.springframework.stereotype.Service
import java.io.InputStream
import javax.crypto.SecretKey


@Service
class AwsStorageService(private val awsS3Client: AmazonS3) {
    private val secretKey: SecretKey = EncryptionUtils.generateKey()

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

        /// Encode the keyName to avoid special characters
        val encryptedKey = EncryptionUtils.encrypt(keyName!!, secretKey)

        awsS3Client.putObject(bucketName, encryptedKey, value, metadata)
        val url = awsS3Client.getUrl(bucketName, encryptedKey)
        return url.path
    }


    fun deleteFile(
        bucketName: String?,
        keyName: String?
    ) {
        awsS3Client.deleteObject(bucketName, keyName)
    }
}