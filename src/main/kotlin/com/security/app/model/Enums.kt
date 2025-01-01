package com.security.app.model

enum class MediaType(val value: String) {
    IMAGE("IMAGE"),
    VIDEO("VIDEO"),
    AUDIO("AUDIO"),
    DOCUMENT("DOCUMENT");

    companion object {
        fun fromStringValue(value: String): MediaType {
            return entries.first { it.value == value }
        }
    }
}