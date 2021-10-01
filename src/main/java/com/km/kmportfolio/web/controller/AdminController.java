package com.km.kmportfolio.web.controller;

import com.km.kmportfolio.web.data.UserQueryRepository;
import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserRepository userRepo;
    private UserQueryRepository userQueryRepository;
    private PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepo, UserQueryRepository userQueryRepository, PasswordEncoder passwordEncoder
    ){
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.userQueryRepository = userQueryRepository;
    }

    @GetMapping
    public String home(){
        return "admin/home";
    }

    @GetMapping("/insertTestUsers")
    public String insertTestUsers(){

        for (int i = 4; i < 100; i++){
            User user = new User("test"+i, passwordEncoder.encode("1"),
                    "lenagend@naver.com");
            userRepo.save(user);
        }

        return "admin/home";
    }

    @GetMapping("/userManagement")
    public String manageUser(Model model, Pageable pageable,
                             @RequestParam(required = false) String SearchOption,
                             @RequestParam(required = false) String SearchText){
        Page<User> userList= userQueryRepository.findAll(pageable);
        model.addAttribute("userList", userList);
        return "admin/userManagement";
    }

//    @PostMapping("/updateUserAuthority")
//    public String updateAuthrity(MasterDTO masterDTO, Model model){
//        User user = userRepo.findByUsername(masterDTO.getUsername());
//        if(user == null){
//            model.addAttribute("notFoundUserName", "일치하는 유저명이 없습니다.");
//            return "master/home";
//        }
//        user.setAuthority(masterDTO.getAuthority());
//        userRepo.save(user);
//        return "master/home";
//    }
}
