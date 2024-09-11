package com.Deekshith.Project.UserService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class userFollowing {

    private int user_id;

    private String followingUserName;
}
