package tesis.backend.backend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/role/{id}/{role}")
    public String addRole(@PathVariable("id") UUID id, @PathVariable("role") Integer role) {
        userService.addRole(id, role);
        return "ok";
    }

}
