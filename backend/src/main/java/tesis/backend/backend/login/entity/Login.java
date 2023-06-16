package tesis.backend.backend.login.entity;

import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
