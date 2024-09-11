package com.Deekshith.Project.ApiGateway.filter;


import com.Deekshith.Project.ApiGateway.Exceptions.NotValidTokenException;
import com.Deekshith.Project.ApiGateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AuthenticationFilter  extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;
    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
          if(routeValidator.isSecured.test((ServerHttpRequest) exchange.getRequest())){

              //header contains token or not
              if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                  throw new RuntimeException("missing authorisation header");
              }

              String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
              if(authHeader != null && authHeader.startsWith("Bearer ")){
                  authHeader = authHeader.substring(7);
              }
              try{
                  jwtUtil.validateToken(authHeader);

                  //getting userName and roles from token
                 String userName =  jwtUtil.extractUserName(authHeader);
                 String roles = jwtUtil.extractRoles(authHeader);

                  request = exchange.getRequest()
                          .mutate()
                          .header("loggedInUser", userName)
                          .header("Roles",roles)
                          .header("Authorization","Bearer "+authHeader)
                          .build();
              } catch (Exception e) {
                  System.out.println("Invalid Access");
                  try {
                      throw new NotValidTokenException("Not a valid Token");
                  } catch (NotValidTokenException ex) {
                      throw new RuntimeException(ex);
                  }
              }
          }
          return chain.filter(exchange.mutate().request(request).build());
        }) ;
    }

    public static class Config{

    }
}
