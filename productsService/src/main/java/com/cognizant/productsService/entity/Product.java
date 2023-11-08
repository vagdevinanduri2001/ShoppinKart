package com.cognizant.productsService.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "products")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME="user_sequence";
    @Id
    private  int productId;

    private ProductCategory productCategory;
    private String productName;
    private double price;
//    @ManyToOne(fetch =
//            FetchType.EAGER,cascade = CascadeType.ALL)
    private Seller Seller;
    
}
