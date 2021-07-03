package com.example.kakaopay_membership.user.service;

import com.example.kakaopay_membership.exception.UserNotFoundException;
import com.example.kakaopay_membership.user.entity.User;
import com.example.kakaopay_membership.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User find(String userId) {
        Optional<User> selectedUser = userRepository.findByUserId(userId);
        User user = selectedUser.orElseThrow(() -> new UserNotFoundException("사용자를 찾을수 없습니다."));
        return user;
    }
}
