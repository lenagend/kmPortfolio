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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public String banned(@RequestParam String username, @RequestParam String process,
                         @RequestParam int stopDate){
        String result = "";
        try{
            User user = userRepo.findByUsername(username);
            switch (process){
                case "banned":
                    user.setEnabled(0);
                    break;
                case "stop":
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    DateFormat df = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");                    cal.add(Calendar.DATE, stopDate);
                    user.setLocked(df.format(cal.getTime()));
                    break;
                case "release":
                    user.setEnabled(1);
                    user.setLocked("nonLocked");
                    break;

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
