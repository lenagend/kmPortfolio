package com.km.kmportfolio.web.user.controller;

import com.km.kmportfolio.web.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model){
        if (user != null){
            model.addAttribute("user", user);
        }
        return "user/home";
    }
}
