package com.cognizant.userManagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtRequestTest {
    JwtRequest jwtRequest = new JwtRequest();
    @Test
    void userNameTest(){
        jwtRequest.setUserName("username");
        assertEquals("username",jwtRequest.getUserName());
    }

    @Test
    void userPasswordTest(){
        jwtRequest.setUserPassword("password");
        assertEquals("password",jwtRequest.getUserPassword());
    }
}
