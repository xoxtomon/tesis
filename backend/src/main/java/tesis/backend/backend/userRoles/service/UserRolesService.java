package tesis.backend.backend.userRoles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesis.backend.backend.userRoles.repository.UserRolesRepository;

@Service
public class UserRolesService {
    @Autowired
    UserRolesRepository userRolesRepository;

}
