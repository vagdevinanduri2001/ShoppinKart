package com.cognizant.userManagement.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {
    Role role = new Role();
    @Test
    void roleNameTest(){
        role.setRoleName("admin");
        assertEquals("admin",role.getRoleName());
    }
}
