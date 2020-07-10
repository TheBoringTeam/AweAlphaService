package com.awe.alpha.controllers

import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.services.AccountService
import com.awe.alpha.utils.exceptions.WrongArgumentsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Controller is responsible for all account related queries
 */
@RestController
@RequestMapping("api/user")
class AccountController @Autowired constructor(val _accountService: AccountService) {

    private val _log = Logger.getLogger(AccountController::class.java)

    @PostMapping("/registration")
    @ResponseBody
    fun register(@Valid @RequestBody accountSignUpForm: AccountSignUpForm, bindingResult: BindingResult,
                 request: HttpServletRequest): ResponseEntity<*> {
        _log.info("Start register process...")

        // validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = _accountService.createAccount(accountSignUpForm)
        return ResponseEntity.status(HttpStatus.CREATED).body(account)
    }
}