//package com.Deekshith.Project.UserService.externals;
//
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Configuration
//public class FeignClientInterceptorConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor(){
//        return  new RequestInterceptor() {
//            @Override
//            public void apply(RequestTemplate requestTemplate) {
//                String token = extractTokenFromContext();
//
//                if(token != null){
//                    requestTemplate.header("Authorization","Bearer "+token);
//                }
//            }
//        };
//    }
//
//    private String extractTokenFromContext() {
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//       if(attributes != null){
//           HttpServletRequest request = attributes.getRequest();
//           String authToken = request.getHeader("Authorization");
//
//           if(authToken != null && authToken.startsWith("Bearer ")){
//               return authToken.substring(7);
//           }
//       }
//       return  null;
//    }
//}
