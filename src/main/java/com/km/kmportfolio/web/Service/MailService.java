package com.km.kmportfolio.web.Service;

import com.km.kmportfolio.web.data.MailDto;
import com.km.kmportfolio.web.util.MailHandler;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "119lenagend119@gmail.com";

    public void mailSend(MailDto mailDto){
        try{
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(mailDto.getAddress());
            mailHandler.setFrom(MailService.FROM_ADDRESS);
            mailHandler.setSubject(mailDto.getTitle());
            mailHandler.setText(mailDto.getMessage(), true);

            mailHandler.send();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
