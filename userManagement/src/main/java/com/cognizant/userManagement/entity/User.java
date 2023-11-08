package com.cognizant.userManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String emailId;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> role;


}
