package com.cognizant.ordersService.service;

import com.cognizant.ordersService.dao.OrdersDao;
import com.cognizant.ordersService.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {
    @Autowired
    private OrdersDao ordersDao;
    @KafkaListener(topics = "my-topic",groupId = "order-group")
    public void listenToTopic(User user){
        String userId = user.getUserId();
        String userName = user.getUserName();
        String emailId = user.getEmailId();

        Seller seller = new Seller(userId,userName,emailId);
        Buyer buyer = new Buyer(userId,userName,emailId);

        List<Order> orders = ordersDao.findByProductsSellerUserIdOrBuyerUserId(userId);

        for(Order order : orders){
            order.setBuyer(buyer);
            List<Product> products = order.getProducts();
            for(Product product:products){
                product.setSeller(seller);
            }
        }
        ordersDao.saveAll(orders);
    }
}
