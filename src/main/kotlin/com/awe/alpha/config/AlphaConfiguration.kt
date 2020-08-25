package com.awe.alpha.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
class AlphaConfiguration @Autowired constructor(val kafkaProperties: KafkaProperties) {

    @Bean
    fun replyingCreateUserTemplate(pf: ProducerFactory<String, String>, factory: ConcurrentKafkaListenerContainerFactory<String, String>)
            : ReplyingKafkaTemplate<String, String, String> {
        // Create response container
        val replyContainer: ConcurrentMessageListenerContainer<String, String> = factory.createContainer("toAlphaResult")
        // Setup container properties
        replyContainer.containerProperties.isMissingTopicsFatal = false
        replyContainer.containerProperties.groupId = "alpha-service-group"
        return ReplyingKafkaTemplate<String, String, String>(pf, replyContainer)
    }
}