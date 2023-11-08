package com.cognizant.userManagement.service;

import com.cognizant.userManagement.dao.RoleDao;
import com.cognizant.userManagement.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role){
        return roleDao.save(role);
    }

}
