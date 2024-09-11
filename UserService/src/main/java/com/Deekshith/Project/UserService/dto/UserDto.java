package com.Deekshith.Project.UserService.dto;

import com.Deekshith.Project.UserService.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private int id;

    private String userName;

    private Set<String> followers = new HashSet<>();

    private Set<String> following = new HashSet<>();

    private List<Integer> postIds;

}
