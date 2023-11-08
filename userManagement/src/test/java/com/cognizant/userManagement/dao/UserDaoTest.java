package com.cognizant.userManagement.dao;

import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByIdTest() {
        Role role = new Role("user");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user1 = new User("username01", "password01", "first01", "user1@email.com", roles);
        testEntityManager.persist(user1);
        User user2 = new User("username02", "password02", "first02",  "user2@email.com", roles);
        testEntityManager.persist(user2);
        User result = userDao.findById(user1.getUserId()).get();
        assertThat(result).isEqualTo(user1);
    }
}
