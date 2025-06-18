package com.example.product.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.Arrays;
import java.util.List;
 
@RestController
@RequestMapping("/products") // This means the endpoint will be /products
public class ProductController {
 
    @GetMapping
    public List<String> getProducts() {
        // In a real application, this would fetch from a database or a service.
        // For this example, we return static data.
        return Arrays.asList("Laptop", "Mouse", "Keyboard", "Monitor", "Webcam");
    }
}