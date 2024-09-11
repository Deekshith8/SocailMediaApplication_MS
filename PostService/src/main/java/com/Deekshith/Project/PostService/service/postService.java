package com.Deekshith.Project.PostService.service;

import com.Deekshith.Project.PostService.Entity.Post;
import com.Deekshith.Project.PostService.KafkaService.ProducerService;
import com.Deekshith.Project.PostService.Repository.IPostRepo;
import com.Deekshith.Project.PostService.dto.PostDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Configuration
@Service
public class postService implements IPostService{


    @Autowired
    private ProducerService producerService;


    private  RestClient restClient;

    public postService(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8083/user")
                .build();
    }


    @Autowired
    private IPostRepo postRepo;
    @Override
    @Transactional
    public ResponseEntity<?> createPost(Post post , String token) {

        try{
            Post temp =  postRepo.save(post);


            producerService.sendPostIdToUser(new int[]{post.getUserId() , post.getId()});

//            ResponseEntity<String>response =  restClient.put()
//                    .uri("/updatePosts/"+temp.getUserId()+"/"+temp.getId())
////                    .contentType(MediaType.APPLICATION_JSON)
////                    .body(post)
//                    .header("Authorization",token)
//                    .exchange((clientRequest, clientResponse) -> {
//
//                        if(clientResponse.getStatusCode().is2xxSuccessful()){
//                            return clientResponse.bodyTo(ResponseEntity.class);
//                        }
//                        throw new RuntimeException("Post is not saved");
//                    });




            return ResponseEntity.status(HttpStatus.CREATED).body("Post is saved");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post is not saved");
        }
    }

    @Override
    public ResponseEntity<?> getPostById(int id) {
        Optional<Post> post = postRepo.findById(id);

        if(post.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(post);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No  User found with id :"+ id);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deletePostById(int id , String token) {
        if(postRepo.existsById(id)) {
            Post post = postRepo.findById(id).get();
            postRepo.deleteById(id);
            Mono<ResponseEntity<String> > response = restClient.delete()
                    .uri("/deletePostOfUser/"+post.getUserId()+"/"+post.getId())
                    .header("Authorization",token)
                    .exchange((clientRequest, clientResponse) -> {

                        if(clientResponse.getStatusCode().is2xxSuccessful()){
                            return clientResponse.bodyTo(Mono.class).map(body-> ResponseEntity.status(HttpStatus.OK).body("Post is saved in User"));
                        }
                        throw new RuntimeException("Post is not saved");
                    });
            //block is used to synchronous calling , else it doesnot care about the api call we made (Async)
            ResponseEntity <String> responseEntity = response.block();
            return ResponseEntity.status(HttpStatus.OK).body("Post deleted with id :" + id);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No post available with id: "+id);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deletePostsOfUser(List<Integer> postsId) {

        try{
            postRepo.deleteAllById(postsId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Override
    public ResponseEntity<String> likePost(int postId, String userName) {
       try {
           Post post = postRepo.findById(postId).get();
           post.getLikedBy().add(userName);
           postRepo.save(post);
           return ResponseEntity.status(HttpStatus.OK).body("Your like added to post");
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("your like is not added to post");
       }
    }




    @Override
    public ResponseEntity<?> getPostsOfUser(List<Integer> postIds) {
        List<Post>posts =  postRepo.findAllById(postIds);

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


}
