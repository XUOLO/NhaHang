package com.example.sv.Service;

import com.example.sv.Model.Product;
import com.example.sv.Model.User;
import com.example.sv.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        this.userRepository.save(user);

    }


    public User viewById(long id) {
        return userRepository.findById(id).get();
    }
    public void changePassword(String username, String newEncodedPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(newEncodedPassword);
            userRepository.save(user);
        }
    }

}
