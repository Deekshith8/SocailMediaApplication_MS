package com.Deekshith.Project.Security.Service.controller;


import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import com.Deekshith.Project.Security.Service.Exceptions.InvalidUsernameAndPasswordException;
import com.Deekshith.Project.Security.Service.service.JwtService;
import com.Deekshith.Project.Security.Service.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/securityService")
public class SecurityController {

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;




    @PostMapping("/register")
    public ResponseEntity<?> saveUserCredentials(@RequestBody UserCredentials userCredentials){
        //by default saving user role as "USER";
        userCredentials.setRoles("USER");
       return securityService.saveUsercredentials(userCredentials);
    }


    @GetMapping("/authenticate")
    public  ResponseEntity<?> generateToken(@RequestBody UserCredentials userCredentials) throws UserPrincipalNotFoundException, InvalidUsernameAndPasswordException {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUserName(), userCredentials.getPassword()));
            if (authenticate.isAuthenticated()) {
                return securityService.generateToken(userCredentials.getUserName());
            } else {
                throw new InvalidUsernameAndPasswordException("Enter valid credentials");
            }
        }catch (Exception e){
            throw new InvalidUsernameAndPasswordException("Enter valid credentials");
        }

    }


    @GetMapping("/validateToken")
    public  String authenticateAndGetToken(@RequestParam("token") String token){

          securityService.validateToken(token);
          return  "token is valid";
    }


//    @GetMapping("/getRolesOfUser/{id}")
//    public void getRolesById(@PathVariable int id){
//       UserCredentials userCredentials =  userCredentialsRepo.findById(id).get();
//        System.out.println(userCredentials.getRoles());
//    }



   //need to send roles of a user by username
    @GetMapping("/getRolesOfUser/{userName}")
    public String getRoles(@PathVariable String userName){
        return securityService.getRolesofUser(userName);
    }


    @DeleteMapping("/deleteUserCredentials/{userName}")
    public ResponseEntity<String> deleteUserCredentials (@PathVariable String userName){
        return securityService.deleteUserCredentials(userName);
    }


}
