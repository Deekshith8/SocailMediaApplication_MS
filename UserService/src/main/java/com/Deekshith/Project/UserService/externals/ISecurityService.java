package com.Deekshith.Project.UserService.externals;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SECURITY-SERVICE" )
public interface ISecurityService {

    @DeleteMapping("/securityService/deleteUserCredentials/{userName}")
    public ResponseEntity<String> deleteUserCredentials (@PathVariable String userName);


}
