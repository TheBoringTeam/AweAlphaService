package com.awe.alpha.services

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

/**
 * Service responsible for connection to Mail Microservice
 */
@Service
class MailService @Autowired constructor(val template: KafkaTemplate<String, String>) {

    private val _log = Logger.getLogger(MailService::class.java)

    fun sendRegistrationMail(email: String) {
    }
}