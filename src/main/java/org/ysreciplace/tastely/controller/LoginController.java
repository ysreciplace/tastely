package org.ysreciplace.tastely.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.UserRepository;
import org.ysreciplace.tastely.request.EmailCheck;
import org.ysreciplace.tastely.service.MailSendService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class LoginController {
    private final MailSendService mailSendService;
    private UserRepository userRepository;

    public LoginController(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    @GetMapping("/login")
    public String loginHandle(Model model){

    return "auth/login";
    }

    @PostMapping("/login")
    public String loginPostHandle(@RequestParam("password") String password,
                                  @ModelAttribute @Valid EmailCheck email, BindingResult result, @RequestParam("username") String userName,
                                  Model model, HttpSession session) {
        if (result.hasErrors()) {
            User user = userRepository.findByUsernameOrEmail(userName);
        }else{
            userRepository.findByUsernameOrEmail(email.getEmail());
        }
        return "redirect:/home";
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
        return "redirect:/index";
    }
}

