package com.learnkafka.service;

import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.Entity.SmsRequestES;
import com.learnkafka.esRepository.ElasticSmsRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
public class SmsMessageServiceEs {
    @Autowired
    ElasticSmsRequestRepository repository;

    public List<SmsRequestES> getAllSms(){
        return repository.findAll(Pageable.unpaged()).toList();
    }

    public SmsRequestES addSms(SmsRequest smsRequest){
        return repository.save(SmsRequestES.builder()
                .smsRequest(smsRequest)
                .build());
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }
}
