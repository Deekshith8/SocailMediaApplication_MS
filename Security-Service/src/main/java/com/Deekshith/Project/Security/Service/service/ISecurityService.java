package com.Deekshith.Project.Security.Service.service;

import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import org.springframework.http.ResponseEntity;

public interface ISecurityService {

    public ResponseEntity<?> saveUsercredentials(UserCredentials user);

    public ResponseEntity<?>generateToken(String userName);

    public void validateToken(String token);


    String getRolesofUser(String userName);

    ResponseEntity<String> deleteUserCredentials(String userName);
}
