package com.awe.alpha.utils.listeners

import com.awe.alpha.services.AccountService
import com.awe.alpha.utils.events.OnRegistrationCompleteEvent
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.message.config.RegistrationListener

@Component
class OnRegistrationCompleteListener @Autowired constructor(private val accountService: AccountService) : ApplicationListener<OnRegistrationCompleteEvent> {

    private val _log = Logger.getLogger(RegistrationListener::class.java)

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        val account = event.account

    }
}