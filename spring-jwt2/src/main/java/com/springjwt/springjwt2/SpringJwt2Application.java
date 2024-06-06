package com.springjwt.springjwt2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringJwt2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwt2Application.class, args);
    }


    @Bean
    @Primary
     public RestTemplate restTemplate()
     {
         return new RestTemplate();
     }

}
