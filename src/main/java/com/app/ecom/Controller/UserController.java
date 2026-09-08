package com.app.ecom.Controller;

import com.app.ecom.Model.User;
import com.app.ecom.DTOs.UserDto;
import com.app.ecom.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsers(@PathVariable long id) {
        return userService.fetchUser(id);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> createUser(@RequestBody UserDto user, @PathVariable long id) {
        return userService.updateUser(user, id);
    }
}
