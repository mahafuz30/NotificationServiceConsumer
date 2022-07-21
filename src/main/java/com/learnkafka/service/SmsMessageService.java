package com.learnkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.repository.SmsRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsMessageService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SendMessageService sendMessageService;

    @Autowired
    SmsRequestRepository smsRequestRepository;

    public void processMessage(ConsumerRecord<Long,String > consumerRecord) throws JsonProcessingException {
        String value = consumerRecord.value();
        SmsRequest smsRequest = objectMapper.readValue(value,SmsRequest.class);
        //TODO:Check Blacklist from Reddis
        //DONE: Send Message via sms api
        sendMessageService.sendMessageRequest(smsRequest, new SendMessageHandler() {
            @Override
            public void onSuccess(SmsRequest smsRequest) {
                smsRequestRepository.save(smsRequest);
                log.info("SMS Message has send successfully {}", smsRequest);
            }

            @Override
            public void onFailure(SmsRequest smsRequest, String failureComments) {
                smsRequestRepository.save(smsRequest);
                log.info("SMS Message has failed to send with failure comment {} and sms request {}",failureComments,smsRequest);

            }
        });
        //TODO: Update to database
        //TODO: Add to elastic Search
    }
}
