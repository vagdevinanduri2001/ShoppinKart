package com.cognizant.userManagement.service;

import com.cognizant.userManagement.dao.UserDao;
import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;

    @Mock
    private KafkaTemplate<String, User> kafkaTemplate;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setup(){
        user = new User("user01","pass01","first01","user@email.com",null);
    }

    @Test
    void getUserDetailsTest(){
        when(userDao.findById(user.getUserId())).thenReturn(Optional.empty());
        User savedUser = userService.getUserDetail(user.getUserId());
        assertThat(savedUser).isNull();
    }

    @Test
    void signUpTestforNewUser(){
        when(passwordEncoder.encode("pass")).thenReturn("pass");
        when(userService.getEncodedPassword("pass")).thenReturn("pass");
        when(userDao.save(user)).thenReturn(user);
        User newUser = userService.signUp(user);
        assertThat(newUser).isNotNull();
    }

    @Test
    void updateUserTest(){
        when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(kafkaTemplate.send(anyString(),any(User.class))).thenReturn(null);
        user.setUserName("mLast");
        user.setEmailId("muser@email.com");
        when(userDao.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser("token",user);
        assertThat(user.getEmailId()).isEqualTo("muser@email.com");
    }

    @Test
    void updateUserRoleTest(){
        when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userDao.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUserRole("token",user.getUserId(),
                new ArrayList<String>(){{
            add("admin");
                }}
        );
        Set<Role> role = new HashSet<>();
        role.add(new Role("admin"));
        user.setRole(role);
        assertThat(updatedUser).isEqualTo(user);
    }

    @Test
    void deleteUserTest(){
        when(userDao.findById(user.getUserId())).thenReturn(Optional.of(user));
        willDoNothing().given(userDao).deleteById(user.getUserId());
        userService.deleteUser(user.getUserId());
        verify(userDao, times(1)).deleteById(user.getUserId());
    }
}
