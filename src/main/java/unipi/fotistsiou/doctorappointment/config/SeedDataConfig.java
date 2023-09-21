package unipi.fotistsiou.doctorappointment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.entity.Role;
import unipi.fotistsiou.doctorappointment.service.RoleService;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SeedDataConfig implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public SeedDataConfig (
        UserService userService,
        RoleService roleService
    ){
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> optionalUser = userService.findOneByEmail("f.tsioumas@domain.com");
        if (optionalUser.isEmpty()) {
            Role doctor = new Role();
            doctor.setName("ROLE_DOCTOR");
            roleService.saveRole(doctor);

            Role patient = new Role();
            patient.setName("ROLE_PATIENT");
            roleService.saveRole(patient);

            User user1 = new User();
            User user2 = new User();

            user1.setFirstName("Φώτης");
            user1.setLastName("Τσιούμας");
            user1.setEmail("f.tsioumas@domain.com");
            user1.setPassword("1234!@#$qwer");
            user1.setTelephone("2101245789");
            user1.setAddress("Πλάτωνος 134, 17674 Καλλιθέα");
            user1.setSpecialization("Παθολόγος");
            Set<Role> roles1 = new HashSet<>();
            roleService.findRoleByName("ROLE_DOCTOR").ifPresent(roles1::add);
            user1.setRoles(roles1);

            user2.setFirstName("Άσπα");
            user2.setLastName("Σιδέρη");
            user2.setEmail("a.sideri@domain.com");
            user2.setPassword("1234!@#$qwer");
            user2.setTelephone("2101248963");
            Set<Role> roles2 = new HashSet<>();
            roleService.findRoleByName("ROLE_PATIENT").ifPresent(roles2::add);
            user2.setRoles(roles2);

            userService.saveUser(user1, user1.getRoles().toString());
            userService.saveUser(user2, user1.getRoles().toString());
        }
    }
}