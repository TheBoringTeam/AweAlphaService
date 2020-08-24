package com.awe.alpha.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {

//    @Value("\${spring.kafka.bootstrapAddress}")
//    private lateinit var servers: String
//
//    @Bean
//    fun producerFactory(): ProducerFactory<String, String> {
//        val config = HashMap<String, Any>()
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers)
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
//        return DefaultKafkaProducerFactory(config)
//    }
//
//    @Bean("kafka")
//    fun kafkaTemplate(): KafkaTemplate<String, String> {
//        return KafkaTemplate(producerFactory())
//    }

}