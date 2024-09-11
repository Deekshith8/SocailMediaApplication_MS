package com.Deekshith.Project.Security.Service.externals;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("USERSERVICE")
public interface IUserService {

    @PostMapping("/user/createUser/{userName}")
    public String createUser(@PathVariable  String userName );

}
