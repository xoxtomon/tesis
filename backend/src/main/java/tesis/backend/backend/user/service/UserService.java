package tesis.backend.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tesis.backend.backend.role.entity.Role;
import tesis.backend.backend.role.repository.RoleRepository;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<String> deletebyId(UUID id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario Eliminado exitosamente.");
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar al usuario, primero elimine sus anteproyectos.");
        }
    }

    public void addRole(UUID id, Integer roleId) {
        // Get the user by uuid
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Get the user's roles
            Set<Role> roles = user.getRoles();
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if(optionalRole.isPresent()){
                Role newrole = optionalRole.get();
                // Add the new role and save it to the repo
                roles.add(newrole);
                userRepository.save(user);
            }
        }
    }
}
