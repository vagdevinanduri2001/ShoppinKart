package com.cognizant.userManagement.dao;

import com.cognizant.userManagement.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleDaoTest {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    void findByIdTest(){
        Role role1 = new Role("admin");
        testEntityManager.persist(role1);

        Role role2 = new Role("user");
        testEntityManager.persist(role2);
        Role result = roleDao.findById(role2.getRoleName()).get();
        assertThat(result).isEqualTo(role2);
    }
}
