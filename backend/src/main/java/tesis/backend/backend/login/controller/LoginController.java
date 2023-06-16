package tesis.backend.backend.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tesis.backend.backend.login.entity.Login;
import tesis.backend.backend.login.service.LoginService;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/provisionallogin")
    public Boolean loginUser(@RequestBody Login login) {
        return loginService.provisionalLogin(login);
    }
}
