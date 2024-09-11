package com.Deekshith.Project.Security.Service.KafkaService;


import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private KafkaTemplate<String , Object> kafkaTemplate;


    public void createUser(String userName) {
        kafkaTemplate.send("SecurityService" , 0, "createUser", userName);
    }
}
