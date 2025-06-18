package com.example.product.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
 
import com.example.product.ProductApplication; // Import your main application class
 
// This annotation tells Spring Boot to load the full application context
// webEnvironment = WebEnvironment.RANDOM_PORT starts the embedded server on a random available port
public class SpringBootProductIntegrationTest {
 
    // Inject the random port that the embedded server started on
    @LocalServerPort
    private int port;
 
    // TestRestTemplate is a convenient REST client for testing
    @Autowired
    private TestRestTemplate restTemplate;
 
    @Test
    void getProducts_ReturnsSuccessStatusCodeAndCorrectContent() {
        // Act: Make an HTTP GET request to the /products endpoint
        // The URL is constructed using the randomly assigned local port
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/products", List.class);
 
        // Assert: Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());
 
        // Assert: Verify the content
        List<String> products = response.getBody();
 
        assertNotNull(products);
        assertEquals(5, products.size());
        assertTrue(products.contains("Laptop"));
        assertTrue(products.contains("Webcam"));
        assertFalse(products.contains("Speaker"));
    }
 
    @Test
    void getProducts_EndpointIsReachable() {
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/products", String.class);
 
        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful()); // Check for 2xx success
    }
} 