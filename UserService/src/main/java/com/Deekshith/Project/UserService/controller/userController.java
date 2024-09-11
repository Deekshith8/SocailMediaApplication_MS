package com.Deekshith.Project.UserService.controller;


import com.Deekshith.Project.UserService.Entity.User;
import com.Deekshith.Project.UserService.Exception.UserNotFoundException;
import com.Deekshith.Project.UserService.dto.Posts;
import com.Deekshith.Project.UserService.dto.UserDto;
import com.Deekshith.Project.UserService.externals.ISecurityService;
import com.Deekshith.Project.UserService.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService iSecurityService;


    @PostMapping("/createUser/{userName}")
    public ResponseEntity<?> createUser(@PathVariable String userName){
       // should create a user from security service when registered
        User temp = new User();
        temp.setUserName(userName);
        return   userService.createUser(temp);
    }

    @GetMapping("/getUserbyId/{id}")
    public ResponseEntity<?> getUserbyId(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestHeader(name = "Roles") String roles){

        if(!roles.equals("ADMIN")){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only admin can see all users");
        }
        List<User> users= userService.getAllUsers();
        return  ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id,@RequestHeader(name = "loggedInUser")String userName,
            @RequestHeader(name = "Roles") String roles , @RequestHeader("Authorization")String token) throws UserNotFoundException {
        return userService.deleteUserById(id , userName , roles , token);
    }

    @PostMapping ("/sendRequest/{receiverId}")
    public ResponseEntity<?> sendRequest( @PathVariable  int receiverId , @RequestHeader(name = "loggedInUser")String userName){
        return userService.sendRequest(userName , receiverId);
    }


    @RequestMapping("/acceptRequest/{from}")
    public ResponseEntity<?> acceptrequest(@PathVariable int from , @RequestHeader(name = "loggedInUser")String userName){
        return  userService.acceptRequest(from, userName);
    }





    @PutMapping("/updatePosts/{userId}/{postId}")
    public ResponseEntity<?> updatePosts(@PathVariable int userId , @PathVariable int postId){
        System.out.println("in update Post");
        return  userService.updatePosts(userId,postId);
    }
//
//
//    @GetMapping("/getPostsOfUser/{userId}")
//    public ResponseEntity<?> getPostsOfUser(@PathVariable int userId){
//        return userService.getPostsOfUser(userId);
//    }
//
//
    @DeleteMapping("/deletePostOfUser/{userId}/{postid}")
    public ResponseEntity<?> deletePostOfUser(@PathVariable int userId,  @PathVariable int postid) throws UserNotFoundException {
        return userService.deletePostOfUser(userId,postid);
    }



    @GetMapping("/getPostOfUser/{userId}")
    public ResponseEntity<?> getPostsOfUser(@PathVariable int userId ) throws UserNotFoundException, InterruptedException {
        return userService.getPostsOfUser(userId);
    }

}
