package com.Deekshith.Project.UserService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Posts {

    private int postId ;

    private String post ;

    private  int userId;

    private List<String> likedBy;

    private List<String> commentedBy;

}
