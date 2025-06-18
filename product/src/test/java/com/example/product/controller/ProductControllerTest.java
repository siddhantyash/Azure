package com.example.product.controller;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ProductControllerTest {

   @Test // Defines a test method
   void getProducts_ReturnsAllProducts() {
       // Arrange (Set up the test data and environment)
       ProductController controller = new ProductController();

       // Act (Perform the action to be tested)
       List<String> products = controller.getProducts();

       // Assert (Verify the outcome)
       assertNotNull(products); // Ensure the result is not null
       assertEquals(5, products.size()); // Check if all 5 expected products are returned
       assertTrue(products.contains("Laptop"));
       assertTrue(products.contains("Webcam"));
       assertFalse(products.contains("Speaker")); // Ensure unexpected products are not present
   }

   @Test
   void getProducts_ReturnsListOfStrings() {
       // Arrange
       ProductController controller = new ProductController();

       // Act
       List<String> products = controller.getProducts();

       // Assert
       assertNotNull(products);
       products.forEach(p -> assertTrue(p instanceof String)); // Ensure all items are strings
   }
}