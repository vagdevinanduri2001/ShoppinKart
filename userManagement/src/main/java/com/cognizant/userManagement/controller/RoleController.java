package com.cognizant.userManagement.controller;

import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping({"/createNewRole"})
    public Role createNewRole(@RequestBody Role role){
        return roleService.createNewRole(role);
    }

}
