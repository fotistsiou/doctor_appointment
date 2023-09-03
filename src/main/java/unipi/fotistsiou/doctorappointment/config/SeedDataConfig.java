package unipi.fotistsiou.doctorappointment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.entity.Role;
import unipi.fotistsiou.doctorappointment.repository.RoleRepository;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.util.HashSet;
import java.util.Set;

@Component
public class SeedDataConfig implements CommandLineRunner {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public SeedDataConfig (
        UserService userService,
        RoleRepository roleRepository
    ){
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role();
        admin.setName("ROLE_USER");
        roleRepository.save(admin);

        Role user = new Role();
        user.setName("ROLE_ADMIN");
        roleRepository.save(user);

        User user1 = new User();
        User user2 = new User();

        user1.setFirstName("user_first");
        user1.setLastName("user_last");
        user1.setEmail("user.user@domain.com");
        user1.setPassword("password");
        user1.setTelephone("2101245789");
        Set<Role> roles1 = new HashSet<>();
        roleRepository.findByName("ROLE_USER").ifPresent(roles1::add);
        user1.setRoles(roles1);

        user2.setFirstName("admin_first");
        user2.setLastName("admin_last");
        user2.setEmail("admin.user@domain.com");
        user2.setPassword("password");
        user2.setTelephone("2101245789");
        user2.setAddress("Platonos 134, 17674 Kallithea, Athens");
        user2.setSpecialization("pathologos");
        Set<Role> roles2 = new HashSet<>();
        roleRepository.findByName("ROLE_ADMIN").ifPresent(roles2::add);
        user2.setRoles(roles2);

        userService.save(user1, user1.getRoles().toString());
        userService.save(user2, user1.getRoles().toString());
    }
}
