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
//
//    @Value("\${spring.kafka.bootstrapAddress}")
//    private lateinit var servers: String

//    @Bean
//    fun kafkaAdmin(): KafkaAdmin {
//        val configs = HashMap<String, Any>()
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers)
//        return KafkaAdmin(configs)
//    }
//
//    @Bean
//    fun topic(): NewTopic {
//        return NewTopic("mytopic", 1, 1)
//    }

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

//    @Bean
//    fun producerConfig(): Map<String, Any> {
//        val props = HashMap<String, Any>(kafkaProperties.buildProducerProperties())
//
//        props[JsonSerializer.ADD_TYPE_INFO_HEADERS] = false
//        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
//        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
//        return props
//    }
//
//    @Bean
//    fun producerFactory(): ProducerFactory<String, Any> {
//        return DefaultKafkaProducerFactory(producerConfig())
//    }
//
//    @Bean
//    fun kafkaTemplate(): KafkaTemplate<String, Any> {
//        return KafkaTemplate(producerFactory())
//    }
}