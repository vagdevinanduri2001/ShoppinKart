package com.cognizant.productsService.service;

import com.cognizant.productsService.dao.ProductsDao;
import com.cognizant.productsService.entity.Product;
import com.cognizant.productsService.entity.Seller;
import com.cognizant.productsService.entity.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {
    @Autowired
    private ProductsDao productsDao;

    @KafkaListener(topics = "my-topic",groupId = "product-group")
    public void listenToTopic(User user){

        String userId = user.getUserId();
        String userName = user.getUserName();
        String emailId = user.getEmailId();

        Seller seller = new Seller(userId,userName,emailId);

        List<Product> products = productsDao.findBySellerUserId(userId);
        //System.out.println(products.get(0));
        for(Product product:products){
            product.setSeller(seller);
        }
        productsDao.saveAll(products);

    }
}
