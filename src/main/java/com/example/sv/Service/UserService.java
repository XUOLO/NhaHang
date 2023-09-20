package com.example.sv.Service;

import com.example.sv.Model.User;
import com.example.sv.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
