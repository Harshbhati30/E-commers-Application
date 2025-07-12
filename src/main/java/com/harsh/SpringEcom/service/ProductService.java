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
    private ProductRepo ProductRepo;

    public List<Product> getAllProducts(){
        return ProductRepo.findAll();
    }

    public Product getProductById(int id) {
        return ProductRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return ProductRepo.save(product);
    }

    public Product updateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return ProductRepo.save(product);
    }

    public void deleteProduct(int id) {
        ProductRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return ProductRepo.searchProducts(keyword);
    }
}
