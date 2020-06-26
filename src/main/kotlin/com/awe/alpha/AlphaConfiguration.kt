
package com.awe.alpha

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.xerial.snappy.buffer.DefaultBufferAllocator.factory


@Configuration
class AlphaConfiguration {

    @Bean
    fun replyingKafkaTemplate(pf: ProducerFactory<String, String>, factory: ConcurrentKafkaListenerContainerFactory<String, String>)
            : ReplyingKafkaTemplate<String, String, String> {
        val replyContainer: ConcurrentMessageListenerContainer<String, String> = factory.createContainer("result")
        replyContainer.getContainerProperties().isMissingTopicsFatal = false
        replyContainer.getContainerProperties().groupId = "alpha-service-group"
        return ReplyingKafkaTemplate<String, String, String>(pf, replyContainer)
    }
}