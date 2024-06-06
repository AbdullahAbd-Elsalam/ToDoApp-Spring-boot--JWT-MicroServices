package com.toda;

import com.toda.model.Item;
import com.toda.model.Item_Details;
import com.toda.model.Priority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodaApplicationTests {

    @Test
    void contextLoads() {
    }


    private RestTemplate restTemplate=new RestTemplate();
    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-DD");

    // make test for founding

    @Test
    public void testSearchItem_Success() {
        // Define the URL
        String url = "/items/1";  // Adjust the path as per your actual endpoint

        // Set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer some-valid-token");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Get the response entity
        ResponseEntity<Item> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Item.class);

        // Use assertEquals to test the status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can add more assertions to check the response body
        Item item = responseEntity.getBody();
        // assertNotNull(item);
        // assertEquals(1, item.getId());
    }
    // make test for insertion products
    @Test
    public void testAddItem_Success() {
        // Define the URL
        String url = "/items";  // Adjust the path as per your actual endpoint

        // Set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer some-valid-token");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Define the request body
        Item_Details itemDetails = new Item_Details();
        itemDetails.setDescription("Sample description");
        itemDetails.setCreatedAt(new Date(System.currentTimeMillis()));
        itemDetails.setPriority(Priority.medium); // Adjust based on your enum
        itemDetails.setStatus(true);

        // Create the request entity
        HttpEntity<Item_Details> entity = new HttpEntity<>(itemDetails, headers);

        // Send the POST request
        ResponseEntity<Item> responseEntity = restTemplate.postForEntity(url, entity, Item.class);

        // Use assertEquals to test the status code
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Optionally, you can add more assertions to check the response body
        Item item = responseEntity.getBody();
        // assertNotNull(item);
        // assertEquals("Sample", item.getTitle()); // Assuming "Sample" is the title extracted from description
        // assertEquals("user@example.com", item.getUserId()); // Assuming the token corresponds to this user
    }

    // make test for deletion of product
    @Test
    public void testDeleteItem_Success() {
        // Define the URL
        String url = "/items/1";  // Adjust the path as per your actual endpoint

        // Set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer some-valid-token");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send the DELETE request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        // Use assertEquals to test the status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Item deleted successfully", responseEntity.getBody());
    }

    @Test
    public void testDeleteItem_Unauthorized() {
        // Define the URL
        String url = "/items/1";  // Adjust the path as per your actual endpoint

        // Set the Authorization header with an invalid token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer some-invalid-token");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send the DELETE request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        // Use assertEquals to test the status code
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid token", responseEntity.getBody());
    }

    // test for update of product
    @Test
    public void testUpdateItem_Success() {
        // Define the URL
        String url = "/item/1";  // Adjust the path as per your actual endpoint

        // Set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer some-valid-token");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Define the request body
        Item_Details itemDetails = new Item_Details();
        itemDetails.setDescription("Updated description");
        itemDetails.setCreatedAt(new Date(System.currentTimeMillis()));
        itemDetails.setPriority(Priority.medium); // Adjust based on your enum
        itemDetails.setStatus(true);

        // Create the request entity
        HttpEntity<Item_Details> entity = new HttpEntity<>(itemDetails, headers);

        // Send the PUT request
        ResponseEntity<Item> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, Item.class);

        // Use assertEquals to test the status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Optionally, you can add more assertions to check the response body
        Item item = responseEntity.getBody();
        // assertNotNull(item);
        // assertEquals("Updated", item.getTitle()); // Assuming "Updated" is the title extracted from description
        // assertEquals("user@example.com", item.getUserId()); // Assuming the token corresponds to this user
    }


}
