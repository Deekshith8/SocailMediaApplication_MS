package com.Deekshith.Project.UserService.externals;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "POSTSERVICE")
public interface IPostService {

    @GetMapping("/getPostsOfUser")
    public ResponseEntity<?> getPostsOfUser(@RequestBody List<Integer> postIds);
}
