package com.Deekshith.Project.PostService.dto;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private  int id ;


    private String post ;

    private  int userId;


    private List<String> likedBy;


    private List<String> commentedBy;



}
