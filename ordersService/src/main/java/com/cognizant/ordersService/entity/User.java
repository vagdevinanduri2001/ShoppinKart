package com.cognizant.ordersService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private String userId;

    private String password;

    private String userName;

    private String emailId;


    private Set<Role> role;

}
