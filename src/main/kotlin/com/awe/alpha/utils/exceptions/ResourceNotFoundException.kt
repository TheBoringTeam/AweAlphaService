package com.awe.alpha.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ResourceNotFoundException(msg: String) : Exception(msg)