package com.Deekshith.Project.PostService.KafkaService;


import com.Deekshith.Project.PostService.Entity.Post;
import com.Deekshith.Project.PostService.dto.PostDto;
import com.Deekshith.Project.PostService.service.IPostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    private  ProducerService producerService;

    @Autowired
    private IPostService iPostService;


    @KafkaListener(topics = "UserService",groupId = "postIdsConsumer" ,
              topicPartitions = @TopicPartition(topic = "UserService",partitions = {"0"}))
    public void consumePostIdsOfUserToDelete(List<Integer>postIds){
        iPostService.deletePostsOfUser(postIds);
    }




}
