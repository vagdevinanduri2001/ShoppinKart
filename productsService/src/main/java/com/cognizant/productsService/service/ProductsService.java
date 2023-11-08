package com.cognizant.productsService.service;

import com.cognizant.productsService.entity.Product;
import com.cognizant.productsService.entity.User;

import java.util.List;

public interface ProductsService {

    public void isRequestValid(int pid, User currentUser,String token);
    public Product addProduct(Product product, User user);
    public Product updateProduct(Product product,User user);
    public boolean deleteProduct(int productId);
    public List<Product> getProducts();
}
