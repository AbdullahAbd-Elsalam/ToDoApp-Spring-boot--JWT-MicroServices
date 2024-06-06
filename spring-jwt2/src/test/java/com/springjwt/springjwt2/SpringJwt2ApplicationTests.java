package com.springjwt.springjwt2;

import com.springjwt.springjwt2.auth.AuthentectionResponse;
import com.springjwt.springjwt2.auth.AuthenticationRequest;
import com.springjwt.springjwt2.auth.RegisterRequest;
import com.springjwt.springjwt2.entity.User;
import com.springjwt.springjwt2.model.request.ChangePasswordRequest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringJwt2ApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
    }


    @Test
    public void testRegister() throws MessagingException {
        // Define the URL
        String url = "/api/v1/auth/register";

        // Define the request body
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        // Set other fields as needed

        // Create the request entity
        HttpEntity<RegisterRequest> entity = new HttpEntity<>(request);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered successfully. Please check your email for verification.", responseEntity.getBody());
    }

    @Test
    public void testAuthenticate() {
        // Define the URL
        String url = "/api/v1/auth/authenticate";

        // Define the request body
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Create the request entity
        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(request);

        // Send the POST request
        ResponseEntity<AuthentectionResponse> responseEntity = restTemplate.postForEntity(url, entity, AuthentectionResponse.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getToken());
    }

    @Test
    public void testActivate() {
        // Define the URL
        String url = "/api/v1/auth/activate/123456/test@example.com";

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Account activated successfully.", responseEntity.getBody());
    }

    @Test
    public void testRegenerateOtp() {
        // Define the URL
        String url = "/api/v1/auth/regenerateOtp/test@example.com";

        // Send the GET request
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("OTP successfully generated and sent to test@example.com", responseEntity.getBody());
    }

    @Test
    public void testForgetPassword() {
        // Define the URL
        String url = "/api/v1/auth/forgetPassword/test@example.com";

        // Send the GET request
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("OTP sent to your email forgetPassword for verification.", responseEntity.getBody());
    }

    @Test
    public void testChangePassword() {
        // Define the URL
        String url = "/api/v1/auth/changePassword";

        // Define the request body
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setEmail("test@example.com");
        request.setNew_password("oldPassword123");

        // Create the request entity
        HttpEntity<ChangePasswordRequest> entity = new HttpEntity<>(request);

        // Send the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        // Use assertEquals to test the status code and response body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password changed successfully.", responseEntity.getBody());
    }


}
