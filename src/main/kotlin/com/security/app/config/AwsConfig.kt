package com.security.app.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AwsConfig {
    private val accessKey: String? = System.getenv("AWS_ACCESS_KEY")

    private val accessSecret: String? = System.getenv("AWS_SECRET_KEY")

    private val region: String? = System.getenv("AWS_REGION")

    @Bean
    fun s3Client(): AmazonS3 {
        val credentials: AWSCredentials = BasicAWSCredentials(accessKey, accessSecret)


        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(region)
            .build()
    }
}
