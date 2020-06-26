package com.awe.alpha

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.xerial.snappy.buffer.DefaultBufferAllocator.factory


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RefreshScope
@Configuration
class AlphaConfiguration {

    @Bean
    fun replyingKafkaTemplate(pf: ProducerFactory<String, String>, factory: ConcurrentKafkaListenerContainerFactory<String, String>)
            : ReplyingKafkaTemplate<*, *, *> {
        // Create response container
        val replyContainer: ConcurrentMessageListenerContainer<String, String> = factory.createContainer("to-alpha-result")
        // Setup container properties
        replyContainer.containerProperties.isMissingTopicsFatal = false
        replyContainer.containerProperties.groupId = "alpha-service-group"
        return ReplyingKafkaTemplate<String, String, String>(pf, replyContainer)
    }
}