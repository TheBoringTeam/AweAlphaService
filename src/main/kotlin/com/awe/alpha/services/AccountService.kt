package com.awe.alpha.services

import com.awe.alpha.persistence.domain.Account
import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.persistence.dto.stream.request.AccountCreateRequestStream
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    private lateinit var _kafkaTemplate: KafkaTemplate<String, String>


    fun updateAccount() {
        TODO("Not yet implemented")
    }

    fun createAccount(signUpForm: AccountSignUpForm) {
        _log.debug("Sending create account event to account service...")

        val producerRecord = ProducerRecord<String, String>("createAccountTopic", null, "createAccount",
                _converter.writeValueAsString(AccountCreateRequestStream(signUpForm)))

        _log.info(producerRecord.value())
        val response = this._replyTemp.sendAndReceive(producerRecord).get()

        _log.debug("Got response from account service + ${response.value()}")
//        return _objectMapper.readValue(response.value(), BasicKafkaResponse::class.java)
    }

    fun findByEmail(email: String) {
        val producerRecord = ProducerRecord<String, String>("findAccountByEmail", null, "createAccount", email)
        val response = _replyTemp.sendAndReceive(producerRecord).get()
    }

    fun findByUsername(username: String): Account {
        val record = ProducerRecord<String, String>("findAccountByUsername", null, "findByUsername", username)
        return _converter.readValue<Account>(_replyTemp.sendAndReceive(record).get().value(), Account::class.java)
    }
}