package com.awe.alpha.controllers

import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.persistence.dto.response.BasicStringResponse
import com.awe.alpha.services.AccountServiceImpl
import com.awe.alpha.utils.exceptions.WrongArgumentsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController("/api/user")

class AccountController @Autowired constructor(val _accountService: AccountServiceImpl) {

    private val _logger = Logger.getLogger(AccountController::class.java)

    @PostMapping("/registration")
    @ResponseBody
    fun register(@Valid @RequestBody accountSignUpForm: AccountSignUpForm, bindingResult: BindingResult,
                 request: HttpServletRequest): ResponseEntity<*> {
        _logger.debug("Start register process")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = _accountService.createAccount(accountSignUpForm)

        _logger.debug("User was created")

        // send registration mail via event
        applicationEventPublisher.publishEvent(OnRegistrationCompleteEvent(account, request.locale, request.contextPath))

        return ResponseEntity.status(HttpStatus.CREATED).body(BasicStringResponse("Account was successfully created"))
    }
}