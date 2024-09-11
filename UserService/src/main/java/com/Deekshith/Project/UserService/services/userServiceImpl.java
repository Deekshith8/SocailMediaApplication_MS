package com.Deekshith.Project.UserService.services;

import com.Deekshith.Project.UserService.Entity.User;
import com.Deekshith.Project.UserService.Exception.InvalidRoleException;
import com.Deekshith.Project.UserService.Exception.UserNotFoundException;
import com.Deekshith.Project.UserService.KafkaService.ConsumerService;
import com.Deekshith.Project.UserService.KafkaService.ProducerService;
import com.Deekshith.Project.UserService.Repository.IuserRepo;
import com.Deekshith.Project.UserService.dto.Posts;
import com.Deekshith.Project.UserService.dto.UserDto;
import com.Deekshith.Project.UserService.dto.userFollower;
import com.Deekshith.Project.UserService.dto.userFollowing;
import com.Deekshith.Project.UserService.externals.IPostService;
import com.Deekshith.Project.UserService.externals.ISecurityService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements IUserService{

    private RestClient restClient;
  public userServiceImpl(){
      this.restClient = RestClient.builder()
              .baseUrl("http://localhost:8081/post")
              .build();
  }

    @Autowired
    private IuserRepo userRepo;

    @Autowired
    private ISecurityService iSecurityService;

    @Autowired
    private IPostService feignPostService;


    @Autowired
    private ProducerService producerService;


    @Override
    public ResponseEntity<?> createUser(User user) {
        try {
            User createdUser = userRepo.save(user);
        }catch (Exception e){
            throw  new RuntimeException("User is not saved");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User is saved");
    }

    @Override
    public ResponseEntity<?> getUserById(int id) {
        Optional<User> user =  userRepo.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepo.findAll();
//        List<UserDto> userDtos = new LinkedList<>();
//
//        for(User user : users){
//
//            Set<String>followersList = new HashSet<>();
//            Set<String> followingList = new HashSet<>();
//            UserDto userDto = new UserDto(user.getUser_id(), user.getUserName(),followersList,followingList,user.getPostIds());
//            userDtos.add(userDto);
//
//        }
        return  users;
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUserById(int id, String userName , String role , String token) throws UserNotFoundException {
        try {
            if (userRepo.existsById(id)) {
                User user = userRepo.findById(id).get();
                if(user.getUserName().equals(userName) || role.equals("Admin")) {

                    //take out this username from followers following set
                    Set<String>followers = user.getFollowers();

                    for(String s:followers){
                        User u =  userRepo.findByuserName(s);
                        u.getFollowing().remove(userName);
                        userRepo.save(u);
                    }


                    Set<String> following = user.getFollowing();
                    for(String s:following){
                        User u =  userRepo.findByuserName(s);
                        u.getFollowers().remove(userName);
                        userRepo.save(u);
                    }

                    List<Integer> postIds = user.getPostIds();
                    userRepo.deleteById(id);
                    // delete the posts by that user id ;
                    // send List of postIds which you are going to them in post Service
//                  ResponseEntity<String>respone =  restClient.method(HttpMethod.DELETE)
//                             .uri("/deletePostsOfUser")
//                            .body(postIds)
//                            .header("Authorization",token)
//                           .exchange((request, respo)->{
//                                if (respo.getStatusCode().is2xxSuccessful()) {
//                                    return respo.bodyTo(ResponseEntity.class);
//                                }
//                                else {
//                                    throw new RuntimeException("Posts are not deleted");
//                                }
//
//                            });  // instead of this we can use Kafka producer
                      producerService.deletepostSOfUser(postIds);


                     //also need to delete from user credentials
                  //  ResponseEntity<String> responseEntity =   iSecurityService.deleteUserCredentials(userName);

                      producerService.deleteUserCredentials(userName);

                       return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");


                }else{
                    throw new InvalidRoleException("you are not authorized to delete");
                }
            }
            else{
                throw new UserNotFoundException("User not found with id "+id);
            }
        } catch (Exception e){
            if(e instanceof  UserNotFoundException){
                throw new UserNotFoundException("User not found with id "+id);
            }
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Deletion is not succeded!! Try again!!");
        }

    }

    @Override
    public ResponseEntity<?> sendRequest(String userName, int receiverId) {

        try {
            User sender = userRepo.findByuserName(userName);
            if (sender != null) {
                User receiver = userRepo.findById(receiverId).get();

                receiver.getRequests().add(sender.getUser_id());
                userRepo.save(receiver);
//          if(requests == null){
//              requests = new ArrayList<>();
//              requests.add(senderId);
//              receiver.setRequests(requests);
//              userRepo.save(receiver);
//          }else{
//              requests.add(senderId);
//              receiver.setRequests(requests);
//              userRepo.save(receiver);
//          }

                return ResponseEntity.status(HttpStatus.OK).body("Request sent to :" + receiver.getUserName());
            }else{
                throw new UserNotFoundException("User not found with id "+receiverId);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Its not you!! Its us!!");
        }
    }

    @Override
    public ResponseEntity<?> acceptRequest(int from, String userName) {
       User toUser =  userRepo.findByuserName(userName);
        if(toUser != null ){
            User fromUser = userRepo.findById(from).get();
            List<Integer> requests = toUser.getRequests();

            if(requests.contains(from)){
                // add him to follower list of myid
                toUser.getFollowers().add( fromUser.getUserName());
                fromUser.getFollowing().add(toUser.getUserName());

                requests.remove((Integer) from);
                toUser.setRequests(requests);
                userRepo.save(toUser);
                userRepo.save(fromUser);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Accepted the request from id "+ from);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Request found from id: "+from);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter the valid UserId");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updatePosts(int userId,int postId) {

        Optional<User> user = userRepo.findById(userId);

        //adding to  his post ids number after every post
        user.get().getPostIds().add(postId);
        userRepo.save(user.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> deletePostOfUser(int userId, int postId) throws UserNotFoundException {
        try{
            if(userRepo.existsById(userId)){
                User user = userRepo.findById(userId).get();
                user.getPostIds().remove((Integer)postId);
                return ResponseEntity.status(HttpStatus.OK).body("Post is removed from users profile too!!");
            }else{
               throw new UserNotFoundException("User not found with id "+userId);
            }

        }catch (Exception e){
            if(e instanceof UserNotFoundException){
                throw new UserNotFoundException("User not found with id "+ userId);
            }
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<?> getPostsOfUser(int userId) throws UserNotFoundException, InterruptedException {

        Optional<User> user = userRepo.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User Not found with id "+userId);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User Found");
        }
        List<Integer> postIds = user.get().getPostIds();

        ResponseEntity<?> postOfUser = feignPostService.getPostsOfUser(postIds);

        if(postOfUser.getStatusCode() .is2xxSuccessful()){
            return postOfUser;
        }
        // check
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldnt able to process your request");
    }




}
