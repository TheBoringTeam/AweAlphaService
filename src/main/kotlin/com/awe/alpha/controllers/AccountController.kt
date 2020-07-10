package com.awe.alpha.controllers

import com.awe.alpha.persistence.dto.request.AccountLoginForm
import com.awe.alpha.persistence.dto.request.AccountSignUpForm
import com.awe.alpha.persistence.dto.response.AccountEntityResponse
import com.awe.alpha.persistence.dto.stream.response.AccountResponse
import com.awe.alpha.security.tokens.JwtTokenProvider
import com.awe.alpha.services.AccountService
import com.awe.alpha.utils.builders.ResponseBuilder
import com.awe.alpha.utils.exceptions.ResourceNotFoundException
import com.awe.alpha.utils.exceptions.WrongArgumentsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Controller is responsible for all account related queries
 */
@RestController
@RequestMapping("api/user")
class AccountController @Autowired constructor(val _accountService: AccountService, val _authManager: AuthenticationManager,
                                               val _tokenProvided: JwtTokenProvider) {

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
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountEntityResponse(account))
    }

    @PostMapping("/sign-in")
    @ResponseBody
    fun signIn(@RequestBody @Valid accountLoginForm: AccountLoginForm, bindingResult: BindingResult): ResponseEntity<*> {
        _log.info("Start login process...")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        _authManager.authenticate(UsernamePasswordAuthenticationToken(accountLoginForm.login, accountLoginForm.password))

        //get authenticated user
        val user: AccountResponse = try { // try to find by email
            _accountService.findByEmail(accountLoginForm.login)
        } catch (e: ResourceNotFoundException) { // if doesn't exists, then find by username
            _accountService.findByUsername(accountLoginForm.login)
        }

        _log.info("User with username ${user.username} was authenticated successfully")

        // return token for user
        val token = _tokenProvided.createToken(user.username, user.permissions)
        return ResponseBuilder().addField("token", token).toJSON()
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    fun getProfileInformation(@PathVariable("uuid") accountUUID: String,
                              @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val account = _accountService.findByUUID(accountUUID)
        return ResponseEntity.ok(AccountEntityResponse(account))
    }
}