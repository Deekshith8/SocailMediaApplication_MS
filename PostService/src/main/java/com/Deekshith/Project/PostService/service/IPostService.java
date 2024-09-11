package com.Deekshith.Project.PostService.service;

import com.Deekshith.Project.PostService.Entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IPostService {
    ResponseEntity<?> createPost(Post post,String token);

    ResponseEntity<?> getPostById(int id);

    ResponseEntity<?> deletePostById(int id , String token);

    ResponseEntity<?> deletePostsOfUser(List<Integer> postsId);

    ResponseEntity<String> likePost(int postId, String userName);


//    public List<Post> getListOfPosts(List<Integer>postIds);


    ResponseEntity<?> getPostsOfUser(List<Integer> postIds);
}
