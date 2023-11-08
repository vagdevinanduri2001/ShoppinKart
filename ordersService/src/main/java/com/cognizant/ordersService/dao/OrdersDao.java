package com.cognizant.ordersService.dao;

import com.cognizant.ordersService.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrdersDao extends MongoRepository<Order,Integer> {
    @Query(value = "{'buyer.userId':?0}")
    List<Order> findByBuyerUserId(String userId);

    @Query(value = "{$or:[{'products.seller.userId':?0},{'buyer.userId':?0}]}")
    List<Order>  findByProductsSellerUserIdOrBuyerUserId(String userId);
}
