package com.learnkafka.service;

import com.learnkafka.Entity.SmsFailureCode;
import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.Entity.SmsRequestStatusType;
import org.springframework.stereotype.Component;

@Component
public class SendMessageService {

    public void sendMessageRequest(SmsRequest smsRequest, SendMessageHandler sendMessageHandler){
        double rand = Math.random();
        if (rand<0.7){
            smsRequest.setSmsRequestStatusType(SmsRequestStatusType.SEND);
            sendMessageHandler.onSuccess(smsRequest);
        }else {
            smsRequest.setSmsRequestStatusType(SmsRequestStatusType.FAILED);
            String failureMessage = "This is a demo rand function";
            smsRequest.setSmsFailureCode(SmsFailureCode.OTHER_FAILURE);
            smsRequest.setFailureMessage(failureMessage);
            sendMessageHandler.onFailure(smsRequest,failureMessage);
        }
    }
}
