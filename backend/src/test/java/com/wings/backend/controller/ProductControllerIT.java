package com.wings.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wings.backend.model.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class ProductControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private <T> List<T> convertToProductList(Object object, TypeReference<List<T>> javaType) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(
            object,
            javaType
        );
    }

    private HttpEntity<Product> createHttpEntity(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(product, headers);
    }

    @Test
    public void testCreateProduct() {
        //GIVEN
        Product product = new Product();
        product.setName("NameTest");
        product.setDescription("DescriptionTest");
        product.setPrice(200.0);

        //WHEN
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(createURLWithPort("/product"), product, Product.class);


        //THEN
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testRetrieveProducts() {
        //GIVEN
        Product product = new Product();
        product.setName("NameTest2");
        product.setDescription("DescriptionTest2");
        product.setPrice(200.0);
        Product product2 = new Product();
        product2.setName("NameTest3");
        product2.setDescription("DescriptionTest3");
        product2.setPrice(300.0);
        restTemplate.postForEntity(createURLWithPort("/product"), product, Product.class);
        restTemplate.postForEntity(createURLWithPort("/product"), product2, Product.class);

        //WHEN
        JsonNode listAsJsonNode = restTemplate.getForObject(createURLWithPort("/product"), JsonNode.class);
        List<Product> retrievedProducts = convertToProductList(listAsJsonNode, new TypeReference<List<Product>>(){});

        //THEN
        assertEquals(4, retrievedProducts.size());
        assertNotNull(retrievedProducts.stream().filter((productX) -> productX.getName().equals("NameTest2")));
        assertNotNull(retrievedProducts.stream().filter((productX) -> productX.getPrice().equals(200.0)));
        assertNotNull(retrievedProducts.stream().filter((productX) -> productX.getName().equals("NameTest3")));
        assertNotNull(retrievedProducts.stream().filter((productX) -> productX.getPrice().equals(300.0)));
    }

    @Test
    public void testRetrieveProduct() {
        //GIVEN
        Product product = new Product();
        product.setName("NameTest4");
        product.setDescription("DescriptionTest4");
        product.setPrice(400.0);
        restTemplate.postForEntity(createURLWithPort("/product"), product, Product.class);

        //WHEN
        ResponseEntity<Optional<Product>> retrievedProductResponse = restTemplate.exchange(
                createURLWithPort("/product/1"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Optional<Product>>() {
                }
        );

        //THEN
        assertEquals(HttpStatus.OK, retrievedProductResponse.getStatusCode());
        assertNotNull(retrievedProductResponse.getBody().get());
        assertEquals("NameTest4", retrievedProductResponse.getBody().get().getName());
        assertEquals("DescriptionTest4", retrievedProductResponse.getBody().get().getDescription());
        assertEquals(400.0, retrievedProductResponse.getBody().get().getPrice());
    }

    @Test
    public void testChangeProduct() {
        // GIVEN
        Product product = new Product();
        product.setName("NameTest4");
        product.setDescription("DescriptionTest4");
        product.setPrice(400.0);
        ResponseEntity<Product> createdProductResponse = restTemplate.postForEntity("/product", product, Product.class);
        Long productId = createdProductResponse.getBody().getId();

        // WHEN
        Product modifiedProduct = new Product();
        modifiedProduct.setName("ModifiedName");
        modifiedProduct.setPrice(500.0);
        ResponseEntity<Product> modifiedProductResponse = restTemplate.exchange(
                 createURLWithPort("/product/") + productId,
                HttpMethod.PATCH,
                createHttpEntity(modifiedProduct),
                Product.class
        );

        // THEN
        assertEquals(HttpStatus.OK, modifiedProductResponse.getStatusCode());
        assertNotNull(modifiedProductResponse.getBody());
        assertEquals("ModifiedName", modifiedProductResponse.getBody().getName());
        assertEquals("DescriptionTest4", modifiedProductResponse.getBody().getDescription());
        assertEquals(500.0, modifiedProductResponse.getBody().getPrice());
    }

}
