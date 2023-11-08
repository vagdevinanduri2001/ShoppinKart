package com.cognizant.ordersService.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

        private  int productId;
        private enum productCategory{Electronics,Books,Fashion};
        private String productName;
        private double price;
        private Seller Seller;


}
