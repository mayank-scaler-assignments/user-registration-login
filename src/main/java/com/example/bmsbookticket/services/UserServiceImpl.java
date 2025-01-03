package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signupUser(String name, String email, String password) throws Exception {
        List<User> usersByEmail = userRepository.getUsersByEmail(email);
        if (!CollectionUtils.isEmpty(usersByEmail)) {
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        user.setPassword(null);
        return user;
    }

    public boolean login(String email, String password) throws Exception {
        List<User> usersByEmail = userRepository.getUsersByEmail(email);
        if (CollectionUtils.isEmpty(usersByEmail)) {
            throw new Exception("User does not exist");
        }
        User user = usersByEmail.get(0);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}