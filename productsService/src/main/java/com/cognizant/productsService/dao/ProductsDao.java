package com.cognizant.productsService.dao;

import com.cognizant.productsService.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsDao extends MongoRepository<Product,Integer> {
    @Query(value = "{'Seller._id':?0}")
    List<Product> findBySellerUserId(String userId);
}
