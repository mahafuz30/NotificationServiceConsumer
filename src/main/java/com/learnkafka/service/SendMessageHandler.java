package com.learnkafka.service;

import com.learnkafka.Entity.SmsRequest;
import org.springframework.stereotype.Component;

@Component
public interface SendMessageHandler {
    void onSuccess(SmsRequest smsRequest);
    void onFailure(SmsRequest smsRequest, String failureComments);
}
