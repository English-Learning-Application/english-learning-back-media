package com.security.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class AppApplication

fun main(args: Array<String>) {
	println("DB_HOST = ${System.getenv("DB_HOST")}")
	println("DB_PORT = ${System.getenv("DB_PORT")}")
	println("DB_NAME = ${System.getenv("DB_NAME")}")
	println("DB_USER = ${System.getenv("DB_USER")}")
	println("DB_PASSWORD = ${System.getenv("DB_PASSWORD")}")
	runApplication<AppApplication>(*args)
}
