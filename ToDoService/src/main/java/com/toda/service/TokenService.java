package com.toda.service;

import com.toda.Exception.NotValidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class TokenService {


    @Autowired
    private RestTemplate restTemplate;

    private final String tokenValidationUrl = "http://localhost:8080/api/v1/user-account/checkToken";

    public boolean isTokenValid(String token) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tokenValidationUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    Boolean.class
            );

            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    }
