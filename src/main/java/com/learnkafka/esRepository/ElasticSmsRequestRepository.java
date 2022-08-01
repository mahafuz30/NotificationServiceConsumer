package com.learnkafka.esRepository;

import com.learnkafka.Entity.SmsRequest;
import com.learnkafka.Entity.SmsRequestES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSmsRequestRepository extends ElasticsearchRepository<SmsRequestES,String> {
}
