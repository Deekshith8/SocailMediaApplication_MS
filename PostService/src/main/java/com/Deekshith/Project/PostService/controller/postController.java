package com.Deekshith.Project.PostService.controller;


import com.Deekshith.Project.PostService.Entity.Post;
import com.Deekshith.Project.PostService.service.IPostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class postController {

    @Autowired
    private IPostService  postService;


    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody Post post,@RequestHeader(value = "Authorization") String token){
        return  postService.createPost(post,token);
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        return  postService.getPostById(id);
    }

    @DeleteMapping("/deletePostById/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable int id , @RequestHeader(value = "Authorization") String token){
        return postService.deletePostById(id , token);
    }


    // from user service when user is deleted
    @DeleteMapping("/deletePostsOfUser")
    public ResponseEntity<?> deletePostsOfUser(@RequestBody List<Integer> postsId){
        return postService.deletePostsOfUser(postsId);
    }

   @PostMapping("/likePostbyId/{postId}")
    public ResponseEntity<String> likePostbyPostId(@PathVariable int postId , @RequestHeader("loggedInUser")String userName){
        return postService.likePost(postId,userName);
   }


   @GetMapping("/getPostsOfUser")
    public ResponseEntity<?>getPostsOfUser(@RequestBody List<Integer> postIds){
        return postService.getPostsOfUser(postIds);
   }
}
