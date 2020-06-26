package com.awe.alpha.services.interfaces

import com.awe.alpha.persistence.domain.Account
import com.awe.alpha.persistence.dto.request.AccountSignUpForm

interface AccountService {
    fun updateAccount()

    fun createAccount(signUpForm: AccountSignUpForm): Account

    fun findByUsername(username: String): Account
}