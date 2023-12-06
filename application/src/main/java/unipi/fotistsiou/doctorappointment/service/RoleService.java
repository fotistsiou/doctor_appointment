package unipi.fotistsiou.doctorappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.doctorappointment.entity.Role;
import unipi.fotistsiou.doctorappointment.repository.RoleRepository;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService (
        RoleRepository roleRepository
    ){
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}