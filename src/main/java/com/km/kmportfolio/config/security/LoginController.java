package com.km.kmportfolio.config.security;


import com.km.kmportfolio.web.Service.MailService;
import com.km.kmportfolio.web.data.MailDto;
import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private static String authorizationRequestBaseUri
            = "http://localhost:9090";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(
            UserRepository userRepo, PasswordEncoder passwordEncoder, MailService mailService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    public String login(Model model){

        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "user/login/form";
    }

    @GetMapping("/signup")
    public String registerForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "user/register/form";
    }

    @ResponseBody
    @GetMapping("/checkUsername")
    public String checkUsername(@RequestParam String username){
        String result = "";
        try{
            User user = userRepo.findByUsername(username);
            if (user == null)
               result = "possible";
            else
               result = "impossible";

        }
        catch (Exception e){
            result = "error";
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationForm registrationForm, Errors errors, Model model){
        Boolean validation = true;
        if(errors.hasErrors())
            validation = false;

        if(userRepo.findByUsername(registrationForm.getUsername()) != null){
            model.addAttribute("overlapError", "이미 존재하는 아이디입니다.");
            validation = false;
        }
        if(!registrationForm.getPassword().equals(registrationForm.getConfirm())){
            model.addAttribute("confirmError", "비밀번호가 일치하지 않습니다.");
            validation = false;
        }
        if (!validation)
        return "user/register/form";

        MailDto mailDto = new MailDto();
        mailDto.setEmail(registrationForm.getEmail());
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