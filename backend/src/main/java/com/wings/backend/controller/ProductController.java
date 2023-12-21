package com.wings.backend.controller;

import com.wings.backend.model.Product;
import com.wings.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product responseProduct = productService.createProduct(product);
        return responseProduct == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(responseProduct);
    }


    @GetMapping
    public ResponseEntity<List<Product>> retrieveProducts() {
        List<Product> products = productService.retrieveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> retrieveProduct(@PathVariable Long id) {
        Optional<Product> product = productService.retrieveProduct(id);
        return product.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> changeProduct(@RequestBody Product product, @PathVariable Long id) {
        Product changedProduct = productService.changeProduct(product, id);
        return ResponseEntity.ok(changedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Produto de ID " + id + " removido com sucesso.");
    }

}
