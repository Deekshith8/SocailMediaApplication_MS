package com.Deekshith.Project.UserService.Repository;

import com.Deekshith.Project.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IuserRepo extends JpaRepository<User, Integer> {


    User findByuserName(String userName);
}
