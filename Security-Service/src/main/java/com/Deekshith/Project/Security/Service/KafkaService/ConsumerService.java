package com.Deekshith.Project.Security.Service.KafkaService;

import com.Deekshith.Project.Security.Service.service.ISecurityService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private ISecurityService iSecurityService;

    @KafkaListener(topics = "UserService",groupId = "userName" ,
                          topicPartitions = @TopicPartition(topic = "UserService",partitions ={"1"}))
    public void deleteUsercredentials(String userName){
        iSecurityService.deleteUserCredentials(userName);
    }

}
