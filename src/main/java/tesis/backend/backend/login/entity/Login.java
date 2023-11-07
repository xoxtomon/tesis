package tesis.backend.backend.login.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Login {
    @JsonProperty("email") // to make requests with key "email" without changing backend logic
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
