package com.wings.backend.service;

import com.wings.backend.model.Product;
import com.wings.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product createProduct(Product product);

    public List<Product> retrieveProducts();

    public Optional<Product> retrieveProduct(Long id);

    public Product changeProduct(Product product, Long id);

    public void deleteProduct(Long id);
}
