package unipi.fotistsiou.doctorappointment.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @Autowired
    public AppointmentController(
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
            @Valid @ModelAttribute("appointment") Appointment appointment,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            model.addAttribute("appointment", appointment);
            return "appointment_new";
        }
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointment/new?success";
    }

    @GetMapping("/appointment/all")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public String getAllAppointments(
        Model model,
        Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = userService.findOneByEmail(authUsername);
        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getId();
            model.addAttribute("userId", userId);
        }
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
            return String.format("redirect:/appointment/my/%d?success", optionalUser.get().getId());
        }
        return "404";
    }

    @GetMapping("/appointment/my/{id}")
    @PreAuthorize("isAuthenticated()")
    public String myAppointments(
            @PathVariable Long id,
            Model model,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = this.userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getEmail().equals(authUsername)) {
                return "404";
            }
            String role = user.getRoles().toString();
            Long userId = user.getId();
            List<Appointment> appointments = appointmentService.getAvailableUserAppointments(id, role);
            model.addAttribute("role", role);
            model.addAttribute("userId", userId);
            model.addAttribute("appointments", appointments);
            return "appointment_my";
        } else {
            return "404";
        }
    }

    @GetMapping("/appointment/cancel/{appId}/{userId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public String cancelAppointment(
            @PathVariable Long appId,
            @PathVariable Long userId,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getEmail().equals(authUsername)) {
                return "404";
            }
            Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(appId);
            if (optionalAppointment.isPresent() && optionalUser.isPresent()) {
                Appointment existingAppointment = optionalAppointment.get();
                existingAppointment.setPatient(null);
                existingAppointment.setReason(null);
                existingAppointment.setBooked(0);
                appointmentService.cancelAppointment(existingAppointment);
                return String.format("redirect:/appointment/my/%d?success", optionalUser.get().getId());
            }
        }
        return "404";
    }

    @GetMapping("/appointment/delete/{appId}/{userId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String deleteAppointment(
            @PathVariable Long appId,
            @PathVariable Long userId,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = this.userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getEmail().equals(authUsername)) {
                return "404";
            }
            Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(appId);
            if (optionalAppointment.isPresent()) {
                Appointment appointment = optionalAppointment.get();
                appointmentService.deleteAppointment(appointment);
                return String.format("redirect:/appointment/my/%d?success_delete", optionalUser.get().getId());
            }
        }
        return "404";
    }
}