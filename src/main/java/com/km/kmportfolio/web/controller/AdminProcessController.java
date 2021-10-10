package com.km.kmportfolio.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.km.kmportfolio.web.Service.MailService;
import com.km.kmportfolio.web.data.MailDto;
import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminProcessController {
    private UserRepository userRepo;
    private MailService mailService;


    public AdminProcessController(UserRepository userRepo, MailService mailService){
        this.userRepo = userRepo;
        this.mailService = mailService;
    }

    @PostMapping("/process")
    public String banned(@RequestParam String username, @RequestParam String process){
        String result = "";
        try{
            User user = userRepo.findByUsername(username);
            switch (process){
                case "banned":
                    user.setEnabled(0);
                    break;
                case "release":
                    user.setEnabled(1);
                    user.setLocked("nonLocked");
            }
            userRepo.save(user);
            Gson gson = new Gson();
            JsonObject jo = new JsonObject();
            jo.addProperty("username", user.getUsername());
            jo.addProperty("email", user.getEmail());
            result = gson.toJson(jo);
        }catch (Exception e){
            e.printStackTrace();
            result = e.toString();
            return result;
        }

        return result;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam Map<String, Object> param){
        System.out.println((String)param.get("email"));
//        MailDto mailDto = new MailDto();
//        mailDto.setAddress((String)param.get("email"));
//        mailDto.setTitle("테스트");
//        String message = "test";
//        mailDto.setMessage(message);
//        mailService.mailSend(mailDto);
        return "";
    }

}
