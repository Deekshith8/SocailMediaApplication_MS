package com.Deekshith.Project.Security.Service.service;


import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import com.Deekshith.Project.Security.Service.KafkaService.ProducerService;
import com.Deekshith.Project.Security.Service.externals.IUserService;
import com.Deekshith.Project.Security.Service.repo.UserCredentialsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl implements ISecurityService {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProducerService producerService;

    @Override
    @Transactional
    public ResponseEntity<?> saveUsercredentials(UserCredentials user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserCredentials userCredentials =    userCredentialsRepo.save(user) ;

        //calling user service when he is registered and storing as a user with the same userName
        //and also sending id for future use

//        iUserService.createUser(user.getUserName());(Replacing with kafka
        producerService.createUser(user.getUserName());
        return  new ResponseEntity<>("User credentials saved",HttpStatus.CREATED) ;
    }

    @Override
    public ResponseEntity<?> generateToken(String username){
        try {
            String token = jwtService.generateToken(username);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sorry, token Service is not available");
        }

    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    @Override
    public String getRolesofUser(String userName) {
        return userCredentialsRepo.findByuserName(userName).getRoles();
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUserCredentials(String userName) {
        UserCredentials userCredentials =  userCredentialsRepo.findByuserName(userName);

        userCredentialsRepo.deleteById(userCredentials.getId());

        return ResponseEntity.status(HttpStatus.OK).body("User credentials Deleted");
    }


}
