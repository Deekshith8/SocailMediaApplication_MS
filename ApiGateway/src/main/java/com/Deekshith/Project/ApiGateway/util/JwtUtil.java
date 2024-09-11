package com.Deekshith.Project.ApiGateway.util;


import com.Deekshith.Project.ApiGateway.Exceptions.NotValidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final  String secret = "DF9vgivoretNgG1dzwwoQntihAUjpBRU59vgivoretNgG1dzwwoQ";

    public void validateToken(final String token) throws NotValidTokenException {

          Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token){
        return extractClaim(token , Claims::getSubject);
    }

    private <T> T extractClaim(String token , Function<Claims,T>claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractRoles(String token){
        final Claims claims = extractAllClaims(token);
        return (String) claims.get("Roles");
    }

}
