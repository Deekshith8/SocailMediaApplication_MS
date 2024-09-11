package com.Deekshith.Project.PostService.KafkaService;

import com.Deekshith.Project.PostService.Entity.Post;
import com.Deekshith.Project.PostService.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

//    public void sendPostsOfUser(List<Post> posts){
//        kafkaTemplate.send("PostService",posts);
//    }

    public void sendPostIdToUser(int[] postIdAndUserId){
        kafkaTemplate.send("PostService", 1,"PostIdToUpdate",postIdAndUserId);
    }


}
