package com.cognizant.ordersService.service;

import com.cognizant.ordersService.entity.Order;
import com.cognizant.ordersService.entity.User;

import java.util.List;

public interface OrdersService {

    public Order placeOrder(Order order,User user);
    public Order updateOrder(Order order,User user);
    public boolean deleteOrder(int orderId);
    public List<Order> getOrders(String buyerUserName);
    public void isRequestValid(int oid, User currentUser, String token);
    public void isRequestValidByBuyer(String buyerUserId, User currentUser, String token);
}
