package com.example.kakaopay_membership;

import com.example.kakaopay_membership.user.entity.User;
import com.example.kakaopay_membership.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("User 조회하기")
    @Test
    void testSelect() {
        User user = new User("kim");
        userRepository.save(user);
        User selectedUser = userRepository.findById(user.getId()).get();
        assertEquals("kim", selectedUser.getUserId());
    }

}
