package com.learnkafka.Entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "sms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SmsRequestES {
    @Id
    @Field(type = FieldType.Auto)
    private String id;

    @Field(type = FieldType.Nested)
    private SmsRequest smsRequest;
}
