package com.Deekshith.Project.UserService.Entity;


import com.Deekshith.Project.UserService.dto.Posts;
import com.Deekshith.Project.UserService.dto.userFollower;
import com.Deekshith.Project.UserService.dto.userFollowing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private  int user_id;

    @Column(name = "UserName",unique = true)
    private String userName;


  //  @OneToMany(targetEntity = userFollowing.class , cascade = CascadeType.ALL)
//    @JoinColumn(name = "following_id" , referencedColumnName = "Id")
//    @EqualsAndHashCode.Exclude
    @ElementCollection
    private Set<String> following = new HashSet<>();


  // @OneToMany(targetEntity = userFollower.class , cascade = CascadeType.ALL)
//    @JoinColumn(name = "followers_id" , referencedColumnName = "Id")
//    @EqualsAndHashCode.Exclude
    @ElementCollection
    private Set<String> followers = new HashSet<>();


    @Column(name = "Requests Received From ")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer>requests ;


    //saving the no of Posts
    @Column(name = "Post Ids")
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> postIds;


//    //adding new id like a foreign key of other service (security credentials id)
//    // it becomes easy when we want to delete a user and also there username and password in security credentials table
//    @Column(name = "User Credentials Id")
//    private int userCredentialsId;



    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", userName='" + userName + '\'' +
                '}';
    }

}
