package com.km.kmportfolio.config.security;

import com.km.kmportfolio.config.security.RegistrationForm;
import com.km.kmportfolio.web.Service.MailService;
import com.km.kmportfolio.web.data.MailDto;
import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
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
public class LoginController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    public LoginController(
            UserRepository userRepo, PasswordEncoder passwordEncoder, MailService mailService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
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
        if(!registrationForm.getPassword().equals(registrationForm.getConfirm())){
            model.addAttribute("confirmError", "비밀번호가 일치하지 않습니다.");
            return "user/register/form";
        }

        MailDto mailDto = new MailDto();
        mailDto.setAddress(registrationForm.getEmail());
        mailDto.setTitle("KmPortFolio 계정 활성화");
        String message = "<a href='http://localhost:9090/activated?username=" + registrationForm.getUsername() + "'>계정 활성화하기</a>";
        mailDto.setMessage(message);
        mailService.mailSend(mailDto);

        userRepo.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/activate";
    }

    @GetMapping("/activate")
    public String activateAccount(){
        return "user/register/activate";
    }

    @GetMapping("/activated")
    public String activated(String username){
        User user = userRepo.findByUsername(username);
        user.setEnabled(1);
        userRepo.save(user);
        return "redirect:/login";
    }

}