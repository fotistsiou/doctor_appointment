package unipi.fotistsiou.doctorappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import unipi.fotistsiou.doctorappointment.entity.Appointment;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.service.AppointmentService;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.security.Principal;
import java.util.Optional;

@Controller
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public AppointmentController (
        AppointmentService appointmentService,
        UserService userService
    ){
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping("/appointment/new")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String createNewPost(Model model, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = userService.findOneByEmail(authUsername);
        if (optionalUser.isPresent()) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(optionalUser.get());
            model.addAttribute("appointment", appointment);
            return "appointment_new";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/appointment/new")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String createNewPost(@ModelAttribute Appointment appointment, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        appointmentService.save(appointment);
        return "redirect:/";
    }
}