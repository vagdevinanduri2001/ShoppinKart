package com.cognizant.userManagement.service;

import com.cognizant.userManagement.entity.AuthenticationResponse;
import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;

import java.util.List;

public interface UserService {

    public String getEncodedPassword(String password);

    public User signUp(User user);

    public User getUserDetail(String userId);

    public User updateUser(String token, User user);

    public User updateUserRole(String token,String userId, List<String> role);

//    public AuthenticationResponse hasUserPermission(String token);
//
//    public AuthenticationResponse hasAdminPermission(String token);

    public boolean deleteUser(String userId);

}
