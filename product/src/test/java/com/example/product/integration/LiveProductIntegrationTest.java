package com.example.product.integration;

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
 
import java.io.IOException;

import java.net.URI;

import java.net.http.HttpClient;

import java.net.http.HttpRequest;

import java.net.http.HttpResponse;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
 
 
public class LiveProductIntegrationTest {
 
    private static HttpClient httpClient;

    private static String baseUrl;

    private static ObjectMapper objectMapper;
 
    @BeforeAll

    static void setup() {

        // Read the base URL from the environment variable set by the Azure DevOps pipeline

        baseUrl = System.getenv("APP_BASE_URL");
 
        if (baseUrl == null || baseUrl.isEmpty()) {

            throw new IllegalStateException("APP_BASE_URL environment variable is not set. Cannot run integration tests against a deployed app.");

        }
 
        httpClient = HttpClient.newBuilder().build();

        objectMapper = new ObjectMapper();

    }
 
    @Test

    void getProducts_ReturnsSuccessStatusCodeAndCorrectContentFromDeployedApp() throws IOException, InterruptedException {

        // Arrange

        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create(baseUrl + "/products"))

                .GET()

                .build();
 
        // Act

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
 
        // Assert status code

        Assertions.assertEquals(200, response.statusCode());
 
        // Assert content

        List<String> products = objectMapper.readValue(response.body(), new TypeReference<List<String>>() {});
 
        Assertions.assertNotNull(products);

        Assertions.assertEquals(5, products.size());

        Assertions.assertTrue(products.contains("Laptop"));

        Assertions.assertTrue(products.contains("Webcam"));

        Assertions.assertFalse(products.contains("Speaker"));

    }
 
    @Test

    void getProducts_EndpointIsReachableOnDeployedApp() throws IOException, InterruptedException {

        // Arrange

        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create(baseUrl + "/products"))

                .GET()

                .build();
 
        // Act

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
 
        // Assert

        Assertions.assertTrue(response.statusCode() >= 200 && response.statusCode() < 300); // Check for 2xx success

    }
 
    @AfterAll

    static void teardown() {

        // Any cleanup if necessary, though for HttpClient, it's often managed by JVM or by explicitly closing

        // if a Closeable resource. For this simple case, no explicit close needed.

    }

}
 