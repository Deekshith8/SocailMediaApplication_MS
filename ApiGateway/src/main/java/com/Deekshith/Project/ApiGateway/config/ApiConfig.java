package com.Deekshith.Project.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {
    @Bean
    public RestTemplate restTemplate(){
       return new RestTemplate();
    }

}
