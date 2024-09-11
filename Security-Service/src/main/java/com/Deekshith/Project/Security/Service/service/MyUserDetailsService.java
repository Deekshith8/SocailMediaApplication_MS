package com.Deekshith.Project.Security.Service.service;


import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import com.Deekshith.Project.Security.Service.config.CustomUserDetails;
import com.Deekshith.Project.Security.Service.repo.UserCredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<UserCredentials> user = Optional.ofNullable(userCredentialsRepo.findByuserName(username));

        return user.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found with user name :" + username));
    }

}
