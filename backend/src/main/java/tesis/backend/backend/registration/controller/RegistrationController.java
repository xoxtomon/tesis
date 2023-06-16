package tesis.backend.backend.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tesis.backend.backend.registration.service.RegistrationService;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.service.UserService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    UserService userService;
    @Autowired
    RegistrationService registrationService;

    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        //return userService.addUser(user);
        return registrationService.addUser(user);
    }
}
