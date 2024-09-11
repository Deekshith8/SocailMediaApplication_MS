package com.Deekshith.Project.UserService.KafkaService;

import com.Deekshith.Project.UserService.Entity.User;
import com.Deekshith.Project.UserService.dto.Posts;
import com.Deekshith.Project.UserService.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
//@Scope(value = Be)
public class ConsumerService {

    @Autowired
    private IUserService userService;

//    private List<Posts> postOfUser;

//    @KafkaListener(topics = "PostService",groupId = "UserConsumer")
//    public void consumePostService(List<Posts> postsOfUser) {
//        System.out.println(postsOfUser);
//        this.postOfUser = postsOfUser;
//    }
//
//
//    public List<Posts> getPosts() {
//      return  this.postOfUser;
//    }



    @KafkaListener(topics = "SecurityService" , groupId = "CreateUserconsumer" ,containerFactory = "kafkaListenerContainerForSecurityService",
            topicPartitions = @TopicPartition(topic = "SecurityService" , partitions = {"0"} ))
    public void createUserConsumer(String userName){
        User user = new User();
        user.setUserName(userName);
        userService.createUser(user);
    }



    @KafkaListener(topics = "PostService" , groupId = "UpdatePostId" ,containerFactory = "kafkaListenerContainerForPostService",
            topicPartitions = @TopicPartition(topic = "PostService" , partitions = {"1"}))
    public void updatePostId(int[]postIdAndUserId){
        int postId = postIdAndUserId[1];
        int userId = postIdAndUserId[0];

        userService.updatePosts(userId , postId);
    }




}
