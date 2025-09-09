
package com.web.e_commers.controller;

import com.web.e_commers.exception.ProductException;
import com.web.e_commers.model.Product;
import com.web.e_commers.request.CreateProductRequest;
import com.web.e_commers.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔍 Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductException {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    // 📦 Get all products with filters
    @GetMapping
    public Page<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(defaultValue = "0") Integer minPrice,
            @RequestParam(defaultValue = "10000") Integer maxPrice,
            @RequestParam(defaultValue = "0") Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
    }

    // 🛠️ Create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws IOException {
        Product product=productService.createProduct(req);
        return ResponseEntity.ok(product);
        		}

    // 🗑️ Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductException {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message); 
    }

    // ✏️ Update product quantity
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product req) throws ProductException {
        Product updated = productService.updateProduct(id, req);
        return ResponseEntity.ok(updated); 
    }
}
