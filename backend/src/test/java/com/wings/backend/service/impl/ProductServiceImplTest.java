package com.wings.backend.service.impl;


import com.wings.backend.model.Product;
import com.wings.backend.repository.ProductRepository;
import com.wings.backend.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void ensureCreateProductMethodIsWorkingProperly() {
        //GIVEN
        Product product = new Product();
        product.setName("NameTest");
        product.setDescription("Description Test");
        product.setPrice(300.00);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //WHEN
        Product productCreated = productService.createProduct(product);

        //THEN
        assertNotNull(productCreated);
        assertNotNull(productCreated.getCreated_at());
        assertNotNull(productCreated.getUpdated_at());
    }

    @Test
    public void ensureRetrieveProductsMethodIsWorkingProperly() {
        //GIVEN
        Date currentDate = new Date();
        Product product = new Product(1L, "TestName", "TestDescription", 200.0, currentDate, currentDate);
        Product secondProduct = new Product(2L, "TestName2", "TestDescription2", 300.0, currentDate, currentDate);
        Product thirdProduct = new Product(3L, "TestName3", "TestDescription3", 400.0, currentDate, currentDate);
        List<Product> products = new ArrayList<Product>(){ {add(product); add(secondProduct); add(thirdProduct); } };
        when(productRepository.findAll()).thenReturn(products);

        //WHEN
        List<Product> productsRetrieved = productService.retrieveProducts();

        //THEN
        assertNotNull(productsRetrieved);
        assertNotEquals(0, productsRetrieved.size());
        assertEquals("TestName", productsRetrieved.get(0).getName());
        assertEquals("TestName2", productsRetrieved.get(1).getName());
        assertEquals("TestName3", productsRetrieved.get(2).getName());
    }

    @Test
    public void ensureRetrieveProductMethodIsWorkingProperly() {
        //GIVEN
        Date currentDate = new Date();
        Product product = new Product(2L, "TestName2", "TestDescription2", 300.0, currentDate, currentDate);
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        //WHEN
        Optional<Product> productRetrieved = productService.retrieveProduct(2L);

        //THEN
        assertNotNull(productRetrieved);
        assertEquals("TestName2", productRetrieved.get().getName());
    }

    @Test
    public void ensureChangeProductMethodIsWorkingProperly() {
        //GIVEN
        Date currentDate = new Date();
        Product product = new Product();
        product.setId(20L);
        product.setName("NameTest");
        product.setDescription("Description Test");
        product.setPrice(300.00);
        product.setCreated_at(new Date());
        when(productRepository.findById(20L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        Product newProduct = new Product();
        newProduct.setName("ChangedName");
        newProduct.setPrice(100.00);



        //WHEN
        Product productRetrieved = productService.changeProduct(newProduct, 20L);

        //THEN
        assertNotNull(productRetrieved);
        assertEquals(20L, productRetrieved.getId());
        assertEquals("ChangedName", productRetrieved.getName());
        assertEquals(100.00, productRetrieved.getPrice());
        assertEquals("Description Test", productRetrieved.getDescription());
        assertNotEquals(productRetrieved.getCreated_at(), productRetrieved.getUpdated_at());
    }

}
