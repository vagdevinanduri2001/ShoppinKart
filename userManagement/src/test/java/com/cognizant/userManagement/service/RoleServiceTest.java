package com.cognizant.userManagement.service;

import com.cognizant.userManagement.dao.RoleDao;
import com.cognizant.userManagement.entity.Role;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;
    @Mock
    private RoleDao roleDao;

    @Test
    void createNewRoleTest(){
        Role role= new Role("admin");

        when(roleDao.save(role)).thenReturn(role);
        Role result = roleService.createNewRole(role);
        assertThat(role).isEqualTo(result);
    }
}
