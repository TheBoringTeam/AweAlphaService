package com.awe.alpha.security.services

import com.awe.alpha.persistence.dto.stream.response.AccountResponse
import com.awe.alpha.services.AccountService
import com.awe.alpha.utils.exceptions.ResourceNotFoundException
import com.awe.alpha.utils.exceptions.TooManyAttemptsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.io.Serializable
import javax.servlet.http.HttpServletRequest


@Service
class AccountUserDetailsService : UserDetailsService, Serializable {
    private val _logger = Logger.getLogger(AccountUserDetailsService::class.java)

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var loginAttemptService: LoginAttemptsService

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun loadUserByUsername(username: String): UserDetails {
        if (username.isEmpty()) {
            throw UsernameNotFoundException("Username is empty")
        }
        val userIp = getClientIP()

        _logger.debug("User trying to log in with ip: [$userIp]")
        if (loginAttemptService.isBlocked(userIp)) {
            throw TooManyAttemptsException("Too many unsuccessful attempts to log in. Please try later")
        }

        val user: AccountResponse = try { // try to find by email
            accountService.findByEmail(username)
        } catch (e: ResourceNotFoundException) { // if doesn't exists, then find by username
            accountService.findByUsername(username)
        }

        val authorities = user.permissions.map { SimpleGrantedAuthority(it) }

        return User(user.username, user.password, authorities)
    }

    private fun getClientIP(): String {
        val xfHeader: String = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
        return xfHeader.split(",").toTypedArray()[0]
    }
}