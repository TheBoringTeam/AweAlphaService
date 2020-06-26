package com.awe.alpha.services

import com.awe.alpha.persistence.domain.Account
import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.services.interfaces.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : AccountService {

    @Autowired
    private lateinit var _replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, Account>


    override fun updateAccount() {
        TODO("Not yet implemented")
    }

    override fun createAccount(signUpForm: AccountSignUpForm): Account {
        TODO("Not yet implemented")
    }

    override fun findByUsername(username: String): Account {
        TODO("Not yet implemented")
    }
}