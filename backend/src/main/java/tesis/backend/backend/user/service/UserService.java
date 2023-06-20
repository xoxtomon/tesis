package tesis.backend.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
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
