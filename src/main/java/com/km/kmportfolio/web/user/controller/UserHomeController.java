package com.km.kmportfolio.web.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHomeController {

    @GetMapping("/")
    public String home(){
        return "user/home";
    }
}
