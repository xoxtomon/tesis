package tesis.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tesis.backend.backend.entity.User;
import tesis.backend.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/provisionallogin/{username}/{password}")
    public Boolean addUser(@PathVariable("username") String username, @PathVariable("password") String password ) {
        return userService.provisionalLogin(username, password);
    }
}
