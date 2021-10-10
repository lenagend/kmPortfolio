package com.km.kmportfolio.web.controller;

import com.km.kmportfolio.web.data.UserQueryRepository;
import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


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

    @GetMapping("/home")
    public String home(){
        return "admin/home";
    }

    @GetMapping("/userControl")
    public String userControl(Model model, Pageable pageable,
                              @RequestParam(required = false) String searchOption,
                              @RequestParam(required = false) String searchText){
        Page<User> boardList = null;
        if(searchOption != null && searchText != null){
            model.addAttribute("searchOption", searchOption);
            model.addAttribute("searchText", searchText);

            if("username".equals(searchOption))
                 boardList = userQueryRepository.findAllByUsernameContainingIgnoreCase(pageable, searchText);
            if("authority".equals(searchOption))
                 boardList = userQueryRepository.findAllByAuthority(pageable, searchText);
            if("enabled".equals(searchOption)){
                if(searchText.equals("활성")){
                    searchText = "1";
                }
                if(searchText.equals("비활성")){
                    searchText = "0";
                }
                boardList = userQueryRepository.findAllByEnabled(pageable, Integer.parseInt(searchText));
            }
            if("regidate".equals(searchOption))
                boardList = userQueryRepository.findAllByRegidateContainingIgnoreCase(pageable, searchText);
        }else{
             boardList= userQueryRepository.findAll(pageable);
        }
        model.addAttribute("boardList", boardList);

        return "admin/userControl";
    }

    @GetMapping("/insertTestUsers")
    public String insertTestUsers(){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        for (int i = 3; i < 100; i++){
            User user = new User("test"+i, passwordEncoder.encode("1"),
                    "lenagend@naver.com", sdf1.format(timestamp));
            userRepo.save(user);
        }

        return "admin/home";
    }

    @GetMapping("/sendEmail")
    public String sendEmail(Model model, @RequestParam String email, @RequestParam String username){
        model.addAttribute("email", email);
        model.addAttribute("username", username);
        return "admin/sendEmail";
    }

}
