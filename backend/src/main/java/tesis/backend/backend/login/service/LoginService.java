package tesis.backend.backend.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.backend.backend.login.entity.Login;
import tesis.backend.backend.registration.response.RegistrationResponse;
import tesis.backend.backend.security.config.JwtService;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

    public ResponseEntity<RegistrationResponse> login(Login login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername()
                , login.getPassword()
                )
        );
        User user = userRepository.findByUsername(login.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String jwtToken = jwtService.generateToken(user);
        RegistrationResponse response = new RegistrationResponse(jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
