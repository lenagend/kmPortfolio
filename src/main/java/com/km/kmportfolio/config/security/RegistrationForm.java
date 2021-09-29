package com.km.kmportfolio.config.security;

import com.km.kmportfolio.web.entity.User;
import lombok.Data;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    @Id
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    private String password;
    private String email1;
    private String email2;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(
                username, passwordEncoder.encode(password), email1, email2
        );
    }
}
