package com.wings.backend.model;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class ProductTest {
    @Test
    public void ensureGetterAndSetterMethodsAreWorkingWell() {
        //GIVEN
        Product product = new Product();
        long testId = 20L;
        String testName = "TestName";
        String testDescription = "TestDescription";
        double testPrice = 500.0;
        Date testDate = new Date();

        //WHEN
        product.setId(testId);
        product.setName(testName);
        product.setDescription(testDescription);
        product.setPrice(testPrice);
        product.setCreated_at(testDate);
        product.setUpdated_at(testDate);
        long retrievedId = product.getId();
        String retrievedName = product.getName();
        String retrievedDescription = product.getDescription();
        double retrievedPrice = product.getPrice();
        Date retrievedCreatedDate = product.getCreated_at();
        Date retrievedUpdatedDate = product.getUpdated_at();

        //THEN
        assertEquals(testId, retrievedId);
        assertEquals(testName, retrievedName);
        assertEquals(testDescription, retrievedDescription);
        assertEquals(testPrice, retrievedPrice);
        assertEquals(testDate, retrievedCreatedDate);
        assertEquals(testDate, retrievedUpdatedDate);
    }
}
