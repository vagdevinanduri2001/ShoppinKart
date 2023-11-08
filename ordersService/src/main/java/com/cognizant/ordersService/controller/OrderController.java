package com.cognizant.ordersService.controller;

import com.cognizant.ordersService.entity.Order;
import com.cognizant.ordersService.entity.User;
import com.cognizant.ordersService.exception.UnAuthorizedUpdateException;
import com.cognizant.ordersService.feignClient.UserFeign;
import com.cognizant.ordersService.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestHeader("Authorization") String token,
                                        @RequestBody Order order) {
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        ordersService.placeOrder(order,user);
        return new ResponseEntity<>("Placed order Successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestHeader("Authorization") String token,
                                         @RequestBody Order order) {
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        ordersService.isRequestValid(order.getOrderId(),user,token);
        Order updatedOrder = ordersService.updateOrder(order,user);
        if(updatedOrder==null){
            return new ResponseEntity<>("Order does  not exist to update",HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity<>("Order updated!",HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteOrders/{orderId}")
    public ResponseEntity<?> deleteOrders(@RequestHeader("Authorization") String token,
                                          @PathVariable int orderId) {
        String userId = userFeign.getUsernameFromToken(token);
        User user = userFeign.getUser(token,userId);
        ordersService.isRequestValid(orderId,user,token);
        boolean response = ordersService.deleteOrder(orderId);
        if(response){
            return new ResponseEntity<>("Order deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Order is not there to delete",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getOrders/{buyerUserName}")
    public List<Order> getOrders(@RequestHeader("Authorization") String token,
                                 @PathVariable String buyerUserName) {
        try {
            String userId = userFeign.getUsernameFromToken(token);
            User user = userFeign.getUser(token,userId);
            ordersService.isRequestValidByBuyer(buyerUserName,user,token);
            return ordersService.getOrders(buyerUserName);
        }catch (UnAuthorizedUpdateException e){
            throw new UnAuthorizedUpdateException("You are not allowed to see other's orders!!!");
        }
        catch(Exception e){
            throw new RuntimeException("Cannot fetch users");
        }
    }
}
