package com.cognizant.userManagement.controller;

import com.cognizant.userManagement.entity.JwtRequest;
import com.cognizant.userManagement.entity.JwtResponse;
import com.cognizant.userManagement.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/login"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{

        return jwtService.createJwtToken(jwtRequest);

    }

}
