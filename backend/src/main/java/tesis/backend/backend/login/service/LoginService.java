package tesis.backend.backend.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.backend.backend.login.entity.Login;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public Boolean isUsernameAvailable(String username) {
        Optional<User> existingUsername = userRepository.findByUsername(username);
        return !existingUsername.isPresent();
    }

    public Boolean provisionalLogin(Login login) {
        //Validate if the user exists by username
        if(!isUsernameAvailable(login.getUsername())) {
            //Get user on success
            Optional<User> optionalUser = userRepository.findByUsername(login.getUsername());

            if(optionalUser.isPresent()) {
                //Access user's properties
                User existingUser = optionalUser.get();
                // Match the given password with the User's encrypted one
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if(bcrypt.matches(login.getPassword(), existingUser.getPassword())){
                    return true;
                }
            }
        }
        return false;
    }
}
