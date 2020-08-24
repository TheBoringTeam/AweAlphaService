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
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service


@Service
class AccountService {

    private val _log = Logger.getLogger(AccountService::class.java)

    // Object mapper
    private val _converter = ObjectMapper()

    @Autowired
    private lateinit var _replyTemp: ReplyingKafkaTemplate<String, String, String>


    fun updateAccount() {
        TODO("Not yet implemented")
    }

    fun createAccount(signUpForm: AccountSignUpForm): AccountResponse {
        _log.debug("Sending create account event to account service...")
        val req = ProducerRecord<String, String>("createAccountTopic", null, "createAccount",
                _converter.writeValueAsString(AccountCreateRequestStream(signUpForm)))
        val res = this._replyTemp.sendAndReceive(req).get().value()
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
        val res = _replyTemp.sendAndReceive(req).get().value()
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
        val res = _replyTemp.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        if (response.isSuccessful) {
            _log.info("AweResponse contains: ${response.value}")
            return _converter.readValue(response.value, AccountResponse::class.java)
        }

        throw ResourceNotFoundException("Account doesn't exist")
    }

    fun findByUUID(uuid: String): AccountResponse {
        val req = ProducerRecord<String, String>("findAccountByUUIDTopic", null, "findByUUID", uuid)
        val res = _replyTemp.sendAndReceive(req).get().value()
        val response = _converter.readValue(res, AweResponse::class.java)

        if (!response.isSuccessful) {
            throw ResourceNotFoundException("Account doesn't exist")
        }

        _log.info("AweResponse contains: ${response.value}")
        return _converter.readValue(response.value, AccountResponse::class.java)
    }
}