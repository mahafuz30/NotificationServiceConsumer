package com.learnkafka.controller;

import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.Entity.SmsRequestES;
import com.learnkafka.esRepository.ElasticSmsRequestRepository;
import com.learnkafka.service.SmsMessageServiceEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("v1/es/sms")
public class SmsElasticsearchController {
    @Autowired
    SmsMessageServiceEs serviceEs;

    @GetMapping
    public List<SmsRequestES> getAllSms(){
        return serviceEs.getAllSms();
    }

    @PostMapping
    public SmsRequestES addSms(@RequestBody SmsRequest smsRequest){
        return serviceEs.addSms(smsRequest);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id){
        serviceEs.deleteById(id);
    }
}
