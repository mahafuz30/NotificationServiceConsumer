package com.learnkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.Entity.SmsFailureCode;
import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.Entity.SmsRequestStatusType;
import com.learnkafka.esRepository.ElasticSmsRequestRepository;
import com.learnkafka.repository.BlackListRepository;
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

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    SmsMessageServiceEs serviceEs;

    public void processMessage(ConsumerRecord<Long,String > consumerRecord) throws JsonProcessingException {
        String value = consumerRecord.value();
        SmsRequest smsRequest = objectMapper.readValue(value,SmsRequest.class);
        if (blackListRepository.findBlackListById(smsRequest.getNumber())!=null){
            smsRequest.setSmsRequestStatusType(SmsRequestStatusType.FAILED);
            smsRequest.setSmsFailureCode(SmsFailureCode.BLACKLISTED);
            smsRequest.setFailureMessage("Black Listed Number");
            smsRequestRepository.save(smsRequest);
            log.info("SMS Message has failed to send; Number Black listed {}",smsRequest);
        }else {
            sendMessageService.sendMessageRequest(smsRequest, new SendMessageHandler() {
                @Override
                public void onSuccess(SmsRequest smsRequest) {
                    smsRequestRepository.save(smsRequest);
                    log.info("SMS Message has send successfully {}", smsRequest);
                    serviceEs.addSms(smsRequest);
                }

                @Override
                public void onFailure(SmsRequest smsRequest, String failureComments) {
                    smsRequestRepository.save(smsRequest);
                    log.info("SMS Message has failed to send with failure comment {} and sms request {}",failureComments,smsRequest);
                    serviceEs.addSms(smsRequest);
                }
            });
        }

    }
}
