package com.harsh.SpringEcom.service;

import com.harsh.SpringEcom.model.Product;
import com.harsh.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    // ✅ Get all products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // ✅ Get product by ID (String)
    public Product getProductById(String id) {
        return productRepo.findById(id).orElse(null);
    }

    // ✅ Add product with image
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
    }

    // ✅ Update product with image (ensure ID is set before calling this)
    public Product updateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product); // save() updates if ID exists
    }

    // ✅ Delete product by ID (String)
    public void deleteProduct(String id) {
        productRepo.deleteById(id);
    }

    // ✅ Search products
    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
