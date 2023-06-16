package tesis.backend.backend.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addUser(User user) {
        if(!isAlreadyRegistered(user.getPersonalId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El documento de identidad ya está asociado a un usuario.");
        }

        if(!isUsernameAvailable(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario no está disponible.");
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hashedPassword = bcrypt.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

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

}
