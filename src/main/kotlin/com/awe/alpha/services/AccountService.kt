package com.awe.alpha.services

import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.persistence.dto.stream.request.AccountCreateRequestStream
import com.awe.alpha.persistence.dto.stream.response.AccountResponse
import com.awe.alpha.persistence.dto.stream.response.AweResponse
import com.awe.alpha.utils.exceptions.ResourceNotFoundException
import com.awe.alpha.utils.exceptions.WrongArgumentsException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service

/**
 * Service responsible for transferring any account related shit with Account Service. Probably it worth to refactor
 * this class, bc a lot of line of code...
 */
@Service
class AccountService @Autowired constructor(
        private val replyTemplate: ReplyingKafkaTemplate<String, String, String>
) {

    private val _log = Logger.getLogger(AccountService::class.java)

    // Object mapper
    private val _converter = ObjectMapper()


    fun updateAccount() {
        TODO("Not yet implemented")
    }

    fun createAccount(signUpForm: AccountSignUpForm): AccountResponse {
        _log.debug("Sending create account event to account service...")
        val req = ProducerRecord<String, String>("createAccountTopic", null, "createAccount",
                _converter.writeValueAsString(AccountCreateRequestStream(signUpForm)))
        val res = replyTemplate.sendAndReceive(req).get().value()
        _log.debug("Response for createAccount: $res")
        val response = _converter.readValue(res, AweResponse::class.java)

        // if response is fine
        if (response.isSuccessful) {
            _log.info("AweResponse contains: ${response.value}")
            return _converter.readValue(response.value, AccountResponse::class.java)
        }

        // throw exception if response is not fine
        // TODO: Write more advanced handle logic! Looks terrible
        throw WrongArgumentsException("Account wasn't created successfully")
    }

    fun findByEmail(email: String): AccountResponse {
        val req = ProducerRecord<String, String>("findAccountByEmailTopic", null, "findByEmail", email)
        val res = replyTemplate.sendAndReceive(req).get().value()
        _log.info("Response for findAccountByEmail: $res")
        val response = _converter.readValue(res, AweResponse::class.java)

        if (response.isSuccessful) {
            _log.info("AweResponse contains: ${response.value}")
            return _converter.readValue(response.value, AccountResponse::class.java)
        }

        // TODO: Write more advanced logic!1!
        throw ResourceNotFoundException("Account doesn't exist")
    }

    fun findByUsername(username: String): AccountResponse {
        val req = ProducerRecord<String, String>("findAccountByUsernameTopic", null, "findByUsername", username)
        val res = replyTemplate.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        if (response.isSuccessful) {
            _log.info("AweResponse contains: ${response.value}")
            return _converter.readValue(response.value, AccountResponse::class.java)
        }

        throw ResourceNotFoundException("Account doesn't exist")
    }

    fun findByUUID(uuid: String): AccountResponse {
        val req = ProducerRecord<String, String>("findAccountByUUIDTopic", null, "findByUUID", uuid)
        val res = replyTemplate.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        if (!response.isSuccessful) {
            throw ResourceNotFoundException("Account doesn't exist")
        }

        _log.info("AweResponse contains: ${response.value}")
        return _converter.readValue(response.value, AccountResponse::class.java)
    }

    fun existsByUsername(username: String): Boolean {
        val req = ProducerRecord<String, String>("existsAccountByUsernameTopic", null, "existsByUsername", username)
        val res = replyTemplate.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        // TODO: Create custom exception for wrong responses from account service
        if (!response.isSuccessful) {
            throw ResourceNotFoundException("Some shit happened during checking whether account exists with provided username")
        }
        _log.info("AweResponse contains: ${response.value}")
        return _converter.readValue(response.value, Boolean::class.java)
    }

    fun existsByEmail(email: String): Boolean {
        // TODO: Could be moved to separate (static?) method
        val req = ProducerRecord<String, String>("existsAccountByEmailTopic", null, "existsByEmail", email)
        val res = replyTemplate.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        if (!response.isSuccessful) {
            throw ResourceNotFoundException("Error during checking email unique values")
        }
        _log.info("AweResponse contains: ${response.value}")
        return _converter.readValue(response.value, Boolean::class.java)
    }
}