package com.cognizant.userManagement.entity;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    User user = new User();

    @Test
    void setUserIdTest(){
        user.setUserId("user01");
        assertEquals("user01",user.getUserId());
    }
    @Test
    void setUserNameTest(){
        user.setUserName("userFirstName");
        assertEquals("userFirstName",user.getUserName());
    }
    @Test
    void setUserPasswordTest(){
        user.setPassword("userPass");
        assertEquals("userPass",user.getPassword());
    }
    @Test
    void setUserEmailTest(){
        user.setEmailId("user@email.com");
        assertEquals("user@email.com",user.getEmailId());
    }
    @Test
    void setRoleTest(){
        Set<Role> role = new HashSet<Role>();
        role.add(new Role("admin"));
        user.setRole(role);
        assertEquals(role,user.getRole());
    }
    @Test
    void notBlankTest(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user1 = new User("","","","",null);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user1);
        assertEquals(3,constraintViolations.size());
    }
    @Test
    void emailValidTest(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user1 = new User("hii","pass","hello","hello",null);
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user1);
        assertEquals(1,constraintViolations.size());
    }
}
