package com.security.app.utils

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object EncryptionUtils {

    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val AES = "AES"

    fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(AES)
        keyGen.init(256) // AES-256
        return keyGen.generateKey()
    }

    fun encrypt(plainText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(16).also { SecureRandom().nextBytes(it) }
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        val encrypted = cipher.doFinal(plainText.toByteArray())

        // Combine IV and encrypted data
        val combined = iv + encrypted

        // Return as Base64 string
        return Base64.getEncoder().encodeToString(combined)
    }
}
