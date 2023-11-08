package com.cognizant.userManagement.service;

import com.cognizant.userManagement.dao.RoleDao;
import com.cognizant.userManagement.dao.UserDao;
import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;
import com.cognizant.userManagement.exception.UserAlreadyExistsException;
import com.cognizant.userManagement.exception.UserNotFoundException;
import com.cognizant.userManagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User signUp(User user) {
        User checkUserExists = getUserDetail(user.getUserId());
        if (checkUserExists != null) throw new UserAlreadyExistsException("User already exists.");
        else {
            String pwd = user.getPassword();
            user.setPassword(getEncodedPassword(pwd));
            return userDao.save(user);
        }
    }

    @Override
    public User getUserDetail(String userId) {
        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) return null;
        return user.get();
    }

    @Override
    public User updateUser(String token, User user) {

//        if (!(jwtUtil.validateToken(token))) {
//            throw new UnauthorizedUserUpdateException("Unauthorized user");
//        } else {
        Optional<User> dbUser = userDao.findById(user.getUserId());
        User outUser = new User();
        if (dbUser.isPresent()) {
            outUser.setUserId(dbUser.get().getUserId());
            String pwd = user.getPassword();
            outUser.setPassword(getEncodedPassword(pwd));
            outUser.setUserName(user.getUserName());
            outUser.setEmailId(user.getEmailId());
            outUser.setRole(dbUser.get().getRole());
            kafkaTemplate.send("my-topic", outUser);
            return userDao.save(outUser);
        } else {
            return null;
        }
    }

    @Override
    public User updateUserRole(String token, String userId, List<String> role) {

        Optional<User> dbUser = userDao.findById(userId);
        User outUser = new User();
        Set<Role> toUpdate = new HashSet<>();
        for (String everyRole : role) {
            Role newRole = new Role();
            newRole.setRoleName(everyRole);
            toUpdate.add(newRole);
        }
        if (dbUser.isPresent()) {
            outUser.setUserId(userId);
            outUser.setPassword(dbUser.get().getPassword());
            outUser.setUserName(dbUser.get().getUserName());
            outUser.setEmailId(dbUser.get().getEmailId());
            outUser.setRole(toUpdate);
            return userDao.save(outUser);
        } else {
            throw new UserNotFoundException("User Not found to update the Role");
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        Optional<User> optional = userDao.findById(userId);
        if (optional.isPresent()) {
            optional.get().setRole(null);
            userDao.deleteById(userId);
        } else {
            return false;
        }
        return true;
    }

}

//    @Override
//    public AuthenticationResponse hasUserPermission(String token) {
//        AuthenticationResponse validity = jwtService.validate(token);
//        if(!jwtService.getRole(validity.getUserId()).contains("user")){
//            return validity;
//        }else{
//            throw new AccessDeniedException("Not Allowed");
//        }
//    }
//
//    @Override
//    public AuthenticationResponse hasAdminPermission(String token) {
//        AuthenticationResponse validity = jwtService.validate(token);
//        if(!jwtService.getRole(validity.getUserId()).contains("admin")){
//            return validity;
//        }else{
//            throw new AccessDeniedException("Not Allowed");
//        }
//    }
