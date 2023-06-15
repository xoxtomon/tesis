package tesis.backend.backend.service;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.backend.backend.entity.User;
import tesis.backend.backend.respository.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> addUser(User user) {
        if(isAlreadyRegistered(user.getPersonalId()) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El documento de identidad ya está asociado a un usuario.");
        }

        if(isUsernameAvailable(user.getUsername()) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario no está disponible.");
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hashedPassword = bcrypt.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        User savedUser = userRepository.save(user);;

        return ResponseEntity.ok(savedUser);
    }

    public Boolean isUsernameAvailable(String username) {
        Optional<User> existingUsername = userRepository.findByUsername(username);
        return !existingUsername.isPresent();
    }

    public Boolean isAlreadyRegistered(int personalId) {
        Optional<User> existingUsername = userRepository.findByPersonalId(personalId);
        return !existingUsername.isPresent();
    }

    public Boolean provisionalLogin(String username, String password) {
        //Validate if the user exists by username
        if(!isUsernameAvailable(username)) {
            //Get user on success
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if(optionalUser.isPresent()) {
                //Access user's properties
                User existingUser = optionalUser.get();
                // Match the given password with the User's encrypted one
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                if(bcrypt.matches(password, existingUser.getPasswordHash())){
                    return true;
                }
            }
        }
        return false;
    }
}
