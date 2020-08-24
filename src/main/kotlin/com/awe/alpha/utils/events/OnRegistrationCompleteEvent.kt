package com.awe.alpha.utils.events

import com.awe.alpha.persistence.dto.stream.response.AccountResponse
import org.springframework.context.ApplicationEvent
import java.util.*

class OnRegistrationCompleteEvent(val account: AccountResponse, val locale: Locale, val appUrl: String) : ApplicationEvent(account)