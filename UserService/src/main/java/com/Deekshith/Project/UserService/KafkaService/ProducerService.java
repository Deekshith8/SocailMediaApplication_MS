package com.Deekshith.Project.UserService.KafkaService;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    @Autowired
    @Qualifier(value = "kafkaTemplateForPostService")
    private KafkaTemplate<String , Object> kafkaTemplateforPostService;

    @Autowired
    @Qualifier(value = "kafkaTemplateForPostServiceForUserCredentials")
    private KafkaTemplate<String,Object> kafkaTemplateForUsercredentialsService;


    public void deletepostSOfUser(List<Integer> postIds){
        kafkaTemplateforPostService.send("UserService" ,0,"deletePosts", postIds);
    }


    public void deleteUserCredentials(String userName){
        //user credentilas will always one, otherwise i need the producer to sent one Integer;
        kafkaTemplateForUsercredentialsService.send("UserService" ,1,"deleteUserCredentials", userName);
    }
}
