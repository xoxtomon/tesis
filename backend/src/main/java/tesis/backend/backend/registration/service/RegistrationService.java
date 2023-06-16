package tesis.backend.backend.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.backend.backend.registration.response.RegistrationResponse;
import tesis.backend.backend.security.config.JwtService;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> register(User user) {
        if(!isAlreadyRegistered(user.getPersonalId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El documento de identidad ya está asociado a un usuario.");
        }

        if(!isUsernameAvailable(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario no está disponible.");
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hashedPassword = bcrypt.encode(user.getPassword());
        // With encoder bean
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserDetails savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        RegistrationResponse response = new RegistrationResponse(jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(response);
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
