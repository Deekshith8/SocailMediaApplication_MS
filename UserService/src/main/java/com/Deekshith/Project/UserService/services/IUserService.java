package com.Deekshith.Project.UserService.services;

import com.Deekshith.Project.UserService.Entity.User;
import com.Deekshith.Project.UserService.Exception.UserNotFoundException;
import com.Deekshith.Project.UserService.dto.Posts;
import com.Deekshith.Project.UserService.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    ResponseEntity<?> createUser(User user);

    ResponseEntity<?> getUserById(int id);

    List<User> getAllUsers();

    ResponseEntity<?> deleteUserById(int id , String userName , String role , String token) throws UserNotFoundException;

    ResponseEntity<?> sendRequest(String userName, int receiverId);

    ResponseEntity<?> acceptRequest(int from,String userName);

    ResponseEntity<?> updatePosts(int userId, int postId);
//
//    ResponseEntity<?> getPostsOfUser(int userId);
//
    ResponseEntity<?> deletePostOfUser(int userId, int postId) throws UserNotFoundException;

    ResponseEntity<?> getPostsOfUser(int userId) throws UserNotFoundException, InterruptedException;
}
