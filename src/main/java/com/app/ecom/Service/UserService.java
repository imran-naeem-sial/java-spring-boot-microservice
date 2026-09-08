package com.app.ecom.Service;


import com.app.ecom.DTOs.UserDto;
import com.app.ecom.Model.User;
import com.app.ecom.Mapper.UserMapper;
import com.app.ecom.Repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static long id = 0L;
    @Autowired
    private UserMapper userMapper;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public ResponseEntity<List<User>> fetchAllUsers() {

        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addUser(User user) {
        this.userRepository.save(user);
        return ResponseEntity.ok("User added successfully!");
    }

    public ResponseEntity<User> fetchUser(long id) {

        return userRepository.findFirstById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<User> updateUser(UserDto user, long id) {
        Optional<User> existingUser = userRepository.findFirstById(id);

        if(!existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("User found with ID " +  id);
        userMapper.updateProductFromDto(user, existingUser.get());

        userRepository.save(existingUser.get());

        return ResponseEntity.ok(existingUser.get());

    }

    public User findUserById(Long id) {
        return userRepository.findFirstById(id).orElse(null);
    }
}
