package com.cognizant.ordersService.service;

import com.cognizant.ordersService.dao.OrdersDao;
import com.cognizant.ordersService.entity.Buyer;
import com.cognizant.ordersService.entity.Order;
import com.cognizant.ordersService.entity.User;
import com.cognizant.ordersService.exception.OrderNotFoundException;
import com.cognizant.ordersService.exception.UnAuthorizedUpdateException;
import com.cognizant.ordersService.feignClient.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrdersService{
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private UserFeign userFeign;

    @Override
    public void isRequestValid(int oid, User currentUser, String token) {
        Optional<Order> order = ordersDao.findById(oid);
        if (!order.isPresent()) {
            throw new OrderNotFoundException("Order " + oid + " is not found");
        }
        Buyer buyer = order.get().getBuyer();
        if (!(userFeign.hasAdminPermission(token,currentUser) || buyer.getUserName().equals(currentUser.getUserName()))) {
            throw new UnAuthorizedUpdateException("Unauthorised");
        }
    }

    @Override
    public void isRequestValidByBuyer(String buyerUserId, User currentUser, String token) {
        if(!(userFeign.hasAdminPermission(token,currentUser)) || buyerUserId.equals(currentUser.getUserId())){
            throw new UnAuthorizedUpdateException("Unauthorised");
        }
    }

    @Override
    public Order placeOrder(Order order,User user) {
        order.setOrderId(sequenceGenerator.getSequenceNumber(Order.SEQUENCE_NAME));
        Buyer buyer = new Buyer(user.getUserId(), user.getUserName(), user.getEmailId());
        order.setBuyer(buyer);
        return ordersDao.save(order);
    }

    @Override
    public Order updateOrder(Order order,User user) {
        Optional<Order> update = ordersDao.findById(order.getOrderId());
        if(update.isPresent()){
            Buyer buyer = new Buyer(user.getUserId(), user.getUserName(), user.getEmailId());
            order.setBuyer(buyer);
            return ordersDao.save(order);
        }
        return null;

    }

    @Override
    public boolean deleteOrder(int orderId) {
        Optional<Order> update = ordersDao.findById(orderId);
        if(update.isPresent()){
            ordersDao.deleteById(orderId);
            return true;
        }
            return false;
    }

    @Override
    public List<Order> getOrders(String buyerUserName) {
        return ordersDao.findByBuyerUserId(buyerUserName);
    }
}
