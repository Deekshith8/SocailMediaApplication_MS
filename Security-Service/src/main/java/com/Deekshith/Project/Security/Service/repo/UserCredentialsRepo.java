package com.Deekshith.Project.Security.Service.repo;

import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepo extends JpaRepository<UserCredentials , Integer> {
    UserCredentials findByuserName(String username);

//    void deleteByuserName(String userName);
}
