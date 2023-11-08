package com.cognizant.productsService;

import com.cognizant.productsService.dao.ProductsDao;
import com.cognizant.productsService.entity.Product;
import com.cognizant.productsService.entity.Seller;
import com.cognizant.productsService.service.ProductsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import static com.cognizant.productsService.entity.ProductCategory.Books;

@ExtendWith(SpringExtension.class)
public class ProductsServiceTest {
    @InjectMocks
    private ProductsServiceImpl productsService;
    @Mock
    private ProductsDao productsDao;
    private Product product;

    @BeforeEach
    void setUp(){
        Seller seller = new Seller("userId","username","mail@mail.com");
        product = new Product(1,Books,"MyBook",100.00,seller);
    }

    @Test
    void getProductDetailTest(){
        when(productsDao.findById(product.getProductId())).thenReturn(Optional.empty());
        Product savedProduct = productsService.getProductDetail(product.getProductId());
        assertThat(savedProduct).isNull();
    }

    @Test
    void addProductTestForSuccess(){

    }


}
