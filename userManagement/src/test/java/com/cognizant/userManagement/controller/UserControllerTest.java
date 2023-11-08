package com.cognizant.userManagement.controller;

import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;
import com.cognizant.userManagement.service.UserService;
import com.cognizant.userManagement.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import  static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.Set;

@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void signUpSuccessTest(){
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role("user"));
//        User user = User.builder()
//                .userId("User")
//                .userName("User")
//                .password("user")
//                .emailId("user@gmail.com")
//                .role(roles)
//                .build();
//
//        given(userService.signUp(any(User.class))).willAnswer(invocation -> invocation.getArguments());
//
//
//
//    }



}
