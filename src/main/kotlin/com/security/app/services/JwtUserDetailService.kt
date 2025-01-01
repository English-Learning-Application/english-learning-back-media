package com.security.app.services

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class JwtUserDetailService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return User.withUsername("admin")
            .password("{noop}admin")
            .roles("ADMIN")
            .build()
    }
}