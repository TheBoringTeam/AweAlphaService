package com.awe.alpha.services

import com.awe.alpha.persistence.domain.Account
import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.persistence.dto.stream.AccountStream
import com.awe.alpha.services.interfaces.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service


@Service
class AccountServiceImpl : AccountService {

    private val _logger = Logger.getLogger(AccountServiceImpl::class.java)

    private val _objectMapper = ObjectMapper()

    @Autowired
    private lateinit var _replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>

//    @Autowired
//    private lateinit var kafkaTemplate: KafkaTemplate<String, String>


    override fun updateAccount() {
        TODO("Not yet implemented")
    }

    override fun createAccount(signUpForm: AccountSignUpForm): Account {


        _logger.debug("Sending create account event to account service...")
        val producerRecord = ProducerRecord<String, String>("testTopic", null,"adwdawdawdawdawd", _objectMapper.writeValueAsString(AccountStream(signUpForm)))
        val future = this._replyingKafkaTemplate.sendAndReceive(producerRecord)
        val response = future.get()
        _logger.debug("Got response from account service + ${response.value()}")


        return _objectMapper.readValue(response.value(), Account::class.java)

//        kafkaTemplate.send("testTopic", "adwdawd", "data from backend")
//        return Account("dawdaw", "Dwadawd", "dawdawd")
    }

    override fun findByUsername(username: String): Account {
        TODO("Not yet implemented")
    }
}