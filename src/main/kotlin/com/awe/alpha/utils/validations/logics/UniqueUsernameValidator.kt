package com.awe.alpha.utils.validations.logics

import com.awe.alpha.services.AccountService
import com.awe.alpha.utils.exceptions.WrongArgumentsException

import com.awe.alpha.utils.validations.annotations.UniqueUsername
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueUsernameValidator : ConstraintValidator<UniqueUsername, String> {

    @Autowired
    lateinit var accountService: AccountService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            throw WrongArgumentsException("Username could not be empty")
        } else {
            return !accountService.existsByUsername(value)
        }
    }
}