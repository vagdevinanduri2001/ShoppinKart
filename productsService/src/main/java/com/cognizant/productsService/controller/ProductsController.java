package com.cognizant.productsService.controller;

import com.cognizant.productsService.entity.Product;
import com.cognizant.productsService.entity.User;
import com.cognizant.productsService.exception.ProductAlreadyExistsException;
import com.cognizant.productsService.feignclient.UserFeign;
import com.cognizant.productsService.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private UserFeign userFeign;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String token,
                                        @RequestBody Product product) {
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        try{
           Product product1 = productsService.addProduct(product,user);
           System.out.println(product1.toString());
            return new ResponseEntity<>("Product added!", HttpStatus.OK);
        }catch(ProductAlreadyExistsException e){
            return new ResponseEntity<>("Product already exists...",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String token,
                                           @RequestBody Product product){
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        productsService.isRequestValid(product.getProductId(), user,token);
        Product updatedProduct = productsService.updateProduct(product,user);
        if(product==null){
            return new ResponseEntity<>("Product does not exist to update",HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity<>("Product details updated",HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String token,
                                           @PathVariable int productId){
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        productsService.isRequestValid(productId, user,token);
        boolean response = productsService.deleteProduct(productId);
        if(response){
            return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product is not there to delete",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts(@RequestHeader("Authorization") String token){
        try {
            String userId = userFeign.getUsernameFromToken(token);
            User user = userFeign.getUser(token, userId);
            return productsService.getProducts();
        }catch (Exception e){
            throw new RuntimeException("Cannot fetch users");
        }
    }


}
