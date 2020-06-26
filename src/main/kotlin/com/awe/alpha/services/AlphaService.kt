package com.awe.alpha.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.message.AddPartitionsToTxnRequestData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Service

@Service
class AlphaService {
    @Autowired
    private lateinit var _replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>

    @Value("\${spring.kafka.template.default-topic}")
    private lateinit var topic: String

    fun sendResponseEvent(): String {
        val producerRecord = ProducerRecord<String, String>("alpha-service-topic", null, "121512", "SomeShitMessage with response")
        val future = this._replyingKafkaTemplate.sendAndReceive(producerRecord)
        val response = future.get()
        return response.value()
    }
}