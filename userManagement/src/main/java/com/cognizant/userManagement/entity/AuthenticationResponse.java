package com.cognizant.userManagement.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {
    private String userId;
    private String name;
    private boolean isValid;
}
