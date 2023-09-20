package unipi.fotistsiou.doctorappointment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.entity.Role;
import unipi.fotistsiou.doctorappointment.repository.RoleRepository;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.util.HashSet;
import java.util.Optional;
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
        Optional<User> optionalUser = userService.findOneByEmail("g.papatsikhs@domain.com");
        if (optionalUser.isEmpty()) {
            Role doctor = new Role();
            doctor.setName("ROLE_DOCTOR");
            roleRepository.save(doctor);

            Role patient = new Role();
            patient.setName("ROLE_PATIENT");
            roleRepository.save(patient);

            User user1 = new User();
            User user2 = new User();

            user1.setFirstName("Γιάννης");
            user1.setLastName("Παπατσίκης");
            user1.setEmail("g.papatsikhs@domain.com");
            user1.setPassword("1234!@#$qwer");
            user1.setTelephone("2101245789");
            user1.setAddress("Πλάτωνος 134, 17674 Καλλιθέα");
            user1.setSpecialization("Παθολόγος");
            Set<Role> roles1 = new HashSet<>();
            roleRepository.findByName("ROLE_DOCTOR").ifPresent(roles1::add);
            user1.setRoles(roles1);

            user2.setFirstName("Πέτρος");
            user2.setLastName("Γαλατας");
            user2.setEmail("p.galatas@domain.com");
            user2.setPassword("1234!@#$qwer");
            user2.setTelephone("2101248963");
            Set<Role> roles2 = new HashSet<>();
            roleRepository.findByName("ROLE_PATIENT").ifPresent(roles2::add);
            user2.setRoles(roles2);

            userService.save(user1, user1.getRoles().toString());
            userService.save(user2, user1.getRoles().toString());
        }
    }
}