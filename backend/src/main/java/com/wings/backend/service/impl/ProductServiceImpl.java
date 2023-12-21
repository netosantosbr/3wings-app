package com.wings.backend.service.impl;

import com.wings.backend.model.Product;
import com.wings.backend.repository.ProductRepository;
import com.wings.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        Date currentDate = new Date();
        product.setCreated_at(currentDate);
        product.setUpdated_at(currentDate);
        return productRepository.save(product);
    }

    public List<Product> retrieveProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> retrieveProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product changeProduct(Product product, Long id) {
        Product existingProduct = productRepository.findById(id).get();
        existingProduct.setName(product.getName() == null ? existingProduct.getName() : product.getName());
        existingProduct.setDescription(product.getDescription() == null ? existingProduct.getDescription() : product.getDescription());
        existingProduct.setPrice(product.getPrice() == null ? existingProduct.getPrice() : product.getPrice());
        existingProduct.setCreated_at(existingProduct.getCreated_at());
        existingProduct.setUpdated_at(new Date());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
