package com.cognizant.userManagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationResponseTest {
    AuthenticationResponse response = new AuthenticationResponse();
    AuthenticationResponse response2 = new AuthenticationResponse();

    @Test
    void setUserIdTest(){
        response.setUserId("User01");
        assertEquals("User01",response.getUserId());
    }
    @Test
    void setNameTest(){
        response.setName("Username01");
        assertEquals("Username01",response.getName());
    }
    @Test
    void setIsValidTest(){
        response.setValid(true);
        assertEquals(true,response.isValid());
    }

    @Test
    void toStringTest(){
        assertEquals(response2.toString(),response.toString());
    }

}
