package com.cognizant.productsService.entity;

import lombok.*;

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
