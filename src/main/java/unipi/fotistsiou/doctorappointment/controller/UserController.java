package unipi.fotistsiou.doctorappointment.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome(
            Model model,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = userService.findOneByEmail(authUsername);
        if (optionalUser.isPresent()) {
            String username = optionalUser.get().getFirstName();
            Long userId = optionalUser.get().getId();
            model.addAttribute("username", username);
            model.addAttribute("userId", userId);
        }
        return "home";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterMidForm() {
        return "register";
    }

    @GetMapping("/register/doctor")
    public String getRegisterAsDoctorForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_doctor";
    }

    @GetMapping("/register/patient")
    public String getRegisterAsPatientForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_patient";
    }

    @PostMapping("/register/{role}")
    public String registerNewUser(
        @PathVariable String role,
        @Valid @ModelAttribute("user") User user,
        BindingResult result,
        Model model
    ){
        Optional<User> optionalUser = userService.findOneByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            result.rejectValue("email", "error.email", "Υπάρχει ήδη ένας λογαριασμός εγγεγραμμένος με αυτό το email. Δοκιμάστε με άλλο λογαριασμό email.");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            if (role.equals("ROLE_DOCTOR")) {
                return "register_doctor";
            } else if (role.equals("ROLE_PATIENT")) {
                return "register_patient";
            }
            return "register";
        }
        userService.saveUser(user, role);
        return "redirect:/login?success_register";
    }

    @GetMapping("/account/info/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getAccountInfo(
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
            model.addAttribute("user", user);
            return "account_info";
        } else {
            return "404";
        }
    }

    @GetMapping("/account/info/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editAccountInfo(
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
            model.addAttribute("user", user);
            return "account_info_edit";
        } else {
            return "404";
        }
    }

    @PostMapping("/account/info/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateAccountInfo (
        @PathVariable Long id,
        User user
    ){
        Optional<User> optionalUser = this.userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setTelephone(user.getTelephone());
            existingUser.setAddress(user.getAddress());
            existingUser.setSpecialization(user.getSpecialization());
            userService.updateUser(existingUser);
        }
        return String.format("redirect:/account/info/%d?success", user.getId());
    }
}
