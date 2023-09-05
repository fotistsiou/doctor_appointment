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
        Role doctor = new Role();
        doctor.setName("ROLE_DOCTOR");
        roleRepository.save(doctor);

        Role patient = new Role();
        patient.setName("ROLE_PATIENT");
        roleRepository.save(patient);

        User user1 = new User();
        User user2 = new User();

        user1.setFirstName("doctor_first");
        user1.setLastName("doctor_last");
        user1.setEmail("doctor.user@domain.com");
        user1.setPassword("1234!@#$qwer");
        user1.setTelephone("2101245789");
        user1.setAddress("Platonos 134, 17674 Kallithea, Athens");
        user1.setSpecialization("pathologos");
        Set<Role> roles1 = new HashSet<>();
        roleRepository.findByName("ROLE_DOCTOR").ifPresent(roles1::add);
        user1.setRoles(roles1);

        user2.setFirstName("patient_first");
        user2.setLastName("patient_last");
        user2.setEmail("patient.user@domain.com");
        user2.setPassword("1234!@#$qwer");
        user2.setTelephone("2101245789");
        Set<Role> roles2 = new HashSet<>();
        roleRepository.findByName("ROLE_PATIENT").ifPresent(roles2::add);
        user2.setRoles(roles2);

        userService.save(user1, user1.getRoles().toString());
        userService.save(user2, user1.getRoles().toString());
    }
}