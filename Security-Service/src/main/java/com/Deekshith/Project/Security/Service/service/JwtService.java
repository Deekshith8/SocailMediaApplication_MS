package com.Deekshith.Project.Security.Service.service;


import com.Deekshith.Project.Security.Service.Entity.UserCredentials;
import com.Deekshith.Project.Security.Service.repo.UserCredentialsRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    public static final  String secret = "DF9vgivoretNgG1dzwwoQntihAUjpBRU59vgivoretNgG1dzwwoQ";

    public void validateToken(final String token){
          Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
    public String generateToken(String userName){
        Map<String , Object> claims = new HashMap<>();
        UserCredentials userCredentials = userCredentialsRepo.findByuserName(userName);
        claims.put("Roles",userCredentials.getRoles());
        return createToken(claims , userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
       return  Jwts.builder()
               .setClaims(claims)
               .setSubject(userName)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*10))
               .signWith(getSignKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public String extractUserName(String token){
//       return   Jwts.claims().getSubject();
//    }

}
