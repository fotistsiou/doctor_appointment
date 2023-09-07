package unipi.fotistsiou.doctorappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import unipi.fotistsiou.doctorappointment.entity.Appointment;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.service.AppointmentService;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentsController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public AppointmentsController(
        AppointmentService appointmentService,
        UserService userService
    ){
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping("/appointment/new")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String createNewAppointment(
        Model model,
        Principal principal
    ){
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
    public String createNewAppointment(
        @ModelAttribute Appointment appointment,
        Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointment/new?success";
    }

    @GetMapping("/appointment/all")
    @PreAuthorize("isAuthenticated()")
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAvailableAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment_all";
    }

    @GetMapping("/appointment/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getAppointmentForEdit(
        @PathVariable Long id,
        Model model
    ) {
        Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            model.addAttribute("appointment", appointment);
            return "appointment_book";
        } else {
            return "404";
        }
    }

    @GetMapping("/appointment/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getAppointment(
        @PathVariable Long id,
        Model model
    ){
        Optional<Appointment> optionalAppointment = this.appointmentService.getAppointmentById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            model.addAttribute("appointment", appointment);
            return "appointment";
        } else {
            return "404";
        }
    }

    @PostMapping("/appointment/{id}")
    @PreAuthorize("isAuthenticated()")
    public String bookAppointment(
        @PathVariable Long id,
        Appointment appointment,
        Principal principal)
    {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = userService.findOneByEmail(authUsername);
        Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(id);
        if (optionalAppointment.isPresent() && optionalUser.isPresent()) {
            Appointment existingAppointment = optionalAppointment.get();
            existingAppointment.setPatient(optionalUser.get());
            existingAppointment.setReason(appointment.getReason());
            appointmentService.saveAppointment(existingAppointment);
        }
        return "redirect:/appointment/" + appointment.getId() + "?success";
    }
}