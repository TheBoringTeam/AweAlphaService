package com.awe.alpha.security.listeners

import com.awe.alpha.security.services.LoginAttemptsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationSuccessEventListener @Autowired constructor(private val loginAttemptsService: LoginAttemptsService) : ApplicationListener<AuthenticationSuccessEvent> {

    override fun onApplicationEvent(event: AuthenticationSuccessEvent) {
        val context = SecurityContextHolder.getContext()
        val auth = context.authentication.details as WebAuthenticationDetails
        loginAttemptsService.loginSucceeded(auth.remoteAddress)
    }
}