package org.ysreciplace.tastely.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.UserRepository;
import org.ysreciplace.tastely.request.LoginRequest;
import org.ysreciplace.tastely.service.MailSendService;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class LoginController {

    private MailSendService mailSendService;
    private UserRepository userRepository;


    @GetMapping("/login")
    public String loginHandle(Model model) {

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginPostHandle(@RequestParam("user-id") String userId,
                                  @RequestParam("password") String password,
                                  HttpSession session) {
        User user = userRepository.findByUsernameOrEmail(userId);
        if (user == null || !user.getPassword().equals(password)){
            return "redirect:/auth/login";
        } else {
            session.setAttribute("user", user);
            return "redirect:/home";
        }
    }

    @GetMapping("/signup")
    public String signupGetHandle(Model model) {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupPostHandle(@ModelAttribute User user) {
        User found = userRepository.findByUsernameOrEmail(user.getEmail());

        if (found == null) {
            user.setProvider("LOCAL");
            user.setVerified("F");
            userRepository.save(user);
            mailSendService.sendWelcomeMessage(user);
        }
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logoutHandle(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}

