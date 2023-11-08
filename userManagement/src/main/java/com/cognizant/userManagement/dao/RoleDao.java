package com.cognizant.userManagement.dao;

import com.cognizant.userManagement.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String>{
}
