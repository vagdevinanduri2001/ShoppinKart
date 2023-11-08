package com.cognizant.userManagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtResponseTest {
    User user = new User();
    JwtResponse jwtResponse = new JwtResponse();

    @Test
    void userTest(){
        jwtResponse.setUser(user);
        assertEquals(user,jwtResponse.getUser());
    }

    @Test
    void tokenTest(){
        jwtResponse.setJwtToken("token");
        assertEquals("token",jwtResponse.getJwtToken());
    }
}
