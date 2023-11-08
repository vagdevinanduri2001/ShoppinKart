package com.cognizant.ordersService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "orders")
public class Order{
    @Transient
    public static final String SEQUENCE_NAME="user_sequence";
    @Id
    private int orderId;
    private List<Product> products;
    private Buyer buyer;

}
