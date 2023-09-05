package unipi.fotistsiou.doctorappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.util.Optional;

@Controller
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(
            UserService userService
    ){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
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
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.save(user, role);
        return "redirect:/login?success_register";
    }
}