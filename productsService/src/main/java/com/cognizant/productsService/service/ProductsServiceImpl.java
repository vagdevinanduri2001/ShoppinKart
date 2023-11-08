package com.cognizant.productsService.service;

import com.cognizant.productsService.dao.ProductsDao;
import com.cognizant.productsService.entity.*;
import com.cognizant.productsService.exception.ProductAlreadyExistsException;
import com.cognizant.productsService.exception.ProductNotFoundException;
import com.cognizant.productsService.exception.UnauthorizedProductUpdateException;
import com.cognizant.productsService.feignclient.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private UserFeign userFeign;

    @Override
    public void isRequestValid(int pid, User currentUser,String token) {
        Optional<Product> product = productsDao.findById(pid);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product " + pid + " is not found");
        }
        Seller seller = product.get().getSeller();
        if (!(userFeign.hasAdminPermission(token,currentUser) || seller.getUserName().equals(currentUser.getUserName()))) {
            throw new UnauthorizedProductUpdateException("Unauthorised");
        }
    }

    public Product getProductDetail(int productId) {
        Optional<Product> product = productsDao.findById(productId);
        if (!product.isPresent()) return null;
        return product.get();
    }

    @Override
    public Product addProduct(Product product, User user) {
        Product checkProductExists = getProductDetail(product.getProductId());
        if (checkProductExists != null) throw new ProductAlreadyExistsException("Product Already exists");
        else {
            Product newProduct = new Product();
            Seller seller = new Seller();
            seller.setUserId(user.getUserId());
            seller.setEmailId(user.getEmailId());
            seller.setUserName(user.getUserName());
            newProduct.setProductId(sequenceGenerator.getSequenceNumber(Product.SEQUENCE_NAME));
            newProduct.setProductName(product.getProductName());
            newProduct.setProductCategory(ProductCategory.valueOf(product.getProductCategory().toString()));
            newProduct.setSeller(seller);
            return productsDao.save(newProduct);
        }

    }

    @Override
    public Product updateProduct(Product product,User user) {
        Optional<Product> update = productsDao.findById(product.getProductId());
        if (update.isPresent()) {
            Product newProduct = new Product();
            Seller seller = new Seller();
            seller.setUserId(user.getUserId());
            seller.setEmailId(user.getEmailId());
            seller.setUserName(user.getUserName());
            newProduct.setProductId(product.getProductId());
            newProduct.setProductName(product.getProductName());
            newProduct.setProductCategory(product.getProductCategory());
            newProduct.setSeller(seller);
            return productsDao.save(newProduct);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        Optional<Product> delete = productsDao.findById(productId);
        if (delete.isPresent()) {
            productsDao.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productsDao.findAll();
    }
}
