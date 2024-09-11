package com.Deekshith.Project.PostService.Entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "Id")
    private  int id ;

    @Column(name = "Post")
    private String post ;

    @Column(name = "UserId")
    private  int userId;

    @Column(name = "Liked By")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> likedBy;

    @Column(name = "Commented By")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> commentedBy;



}
