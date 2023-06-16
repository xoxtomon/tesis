package tesis.backend.backend.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tesis.backend.backend.role.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

// Used on registration to ensure role 1 is not assigned to any other user than admin
    public List<Integer> PruneAdminRole(List<Integer> roleIds) {
        if(roleIds.contains(1)){roleIds.remove(Integer.valueOf(1));}
        return roleIds;
    }

}
