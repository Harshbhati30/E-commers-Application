package com.harsh.SpringEcom.controller;

import com.harsh.SpringEcom.model.Product;
import com.harsh.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    // ✅ Get all products
    @GetMapping("/products")
    public List<Product> getProduct() {
        return service.getAllProducts();
    }

    // ✅ Get product by ID (String)
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Get product image by ID (String)
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable String productId) {
        Product product = service.getProductById(productId);
        if (product != null && product.getImageData() != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", product.getImageType())
                    .body(product.getImageData());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Add product with image
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Update product by ID (String)
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        try {
            product.setId(id); // Set ID manually to ensure correct update
            service.updateProduct(product, imageFile);
            return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Delete product by ID (String)
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        Product product = service.getProductById(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Search products by keyword
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
