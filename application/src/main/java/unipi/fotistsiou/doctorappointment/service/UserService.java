package unipi.fotistsiou.doctorappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.doctorappointment.entity.Role;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService (
        UserRepository userRepository,
        RoleService roleService,
        PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public User saveUser(User user, String role) {
        if (user.getId() == null) {
            if (user.getRoles().isEmpty()) {
                Set<Role> roles = new HashSet<>();
                roleService.findRoleByName(role).ifPresent(roles::add);
                user.setRoles(roles);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTelephone(),
                user.getAddress(),
                user.getSpecialization()
        );
    }
}