package com.km.kmportfolio.config.security;

import com.km.kmportfolio.web.entity.User;
import lombok.Builder;
import lombok.Data;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
public class RegistrationForm {

    @Id
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8자 이상 16자 이하, 대소문자 혼용, 특수문자를 1개 이상 포함해 주세요.")
    private String password;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirm;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    public User toUser(PasswordEncoder passwordEncoder){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new User(
                username, passwordEncoder.encode(password), email, sdf1.format(timestamp)
        );
    }
}
