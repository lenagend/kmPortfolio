package com.km.kmportfolio.web.user.controller;

import com.km.kmportfolio.config.security.RegistrationForm;
import com.km.kmportfolio.web.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserLoginController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserLoginController(
            UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String registerForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "user/register/form";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationForm registrationForm, Errors errors, Model model){
        if(errors.hasErrors()){
            return "user/register/form";
        }
        if(userRepo.findByUsername(registrationForm.getUsername()) != null){
            model.addAttribute("overlapError", "이미 존재하는 아이디입니다.");
            return "user/register/form";

        }
        //userRepo.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}