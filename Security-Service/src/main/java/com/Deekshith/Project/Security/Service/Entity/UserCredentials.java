package com.Deekshith.Project.Security.Service.Entity;


import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;


import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "UserCredentials")
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Username",unique = true)
    private String userName;

    @Column(name = "Password")
    private String password;


    //taking roles as string , and make it as comma separated

   // private String roles = "USER";
   @Column(name = "ROLES")
    private String Roles;


}
