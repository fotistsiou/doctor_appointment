package unipi.fotistsiou.doctorappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import unipi.fotistsiou.doctorappointment.entity.User;
import unipi.fotistsiou.doctorappointment.service.UserService;
import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome(Model model, Principal principal) {
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
}