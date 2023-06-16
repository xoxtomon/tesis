package tesis.backend.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesis.backend.backend.role.entity.Role;
import tesis.backend.backend.role.repository.RoleRepository;
import tesis.backend.backend.user.entity.User;
import tesis.backend.backend.user.respository.UserRepository;

import javax.swing.text.html.Option;
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

    /*public void addRole(Integer id, Integer role) {
        Optional<User> optionalUser = userRepository.findByPersonalId(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Role> userRoles = user.getRoles();

            Optional<Role> optionalRole = roleRepository.findById(role);
            if(optionalRole.isPresent()){
                Role existingRole = optionalRole.get();
                userRoles.add(existingRole);

                userRepository.save(user);
            }
        }
    }*/
    public void addRole(UUID id, Integer roleId) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<Role> roles = user.getRoles();
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            if(optionalRole.isPresent()){
                Role newrole = optionalRole.get();
                roles.add(newrole);
                System.out.println(user);
                userRepository.save(user);
            }
        }
    }
}
