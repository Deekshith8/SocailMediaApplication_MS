package com.Deekshith.Project.ApiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
      "/securityService/register",
      "/securityService/authenticate",
            "/eureka"
    );


    public Predicate<ServerHttpRequest> isSecured =  serverHttpRequest -> {
        boolean b = openApiEndpoints.stream()
                .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri)
                );

        return b;
    };

}
