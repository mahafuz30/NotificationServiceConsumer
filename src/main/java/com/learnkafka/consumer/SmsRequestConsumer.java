package com.learnkafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.service.SmsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsRequestConsumer {
    @Autowired
    SmsMessageService smsMessageService;

    @KafkaListener(topics = {"sms-request"})
    public void onMessageReceived(ConsumerRecord<Long,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {}", consumerRecord);
        smsMessageService.processMessage(consumerRecord);
    }
}
