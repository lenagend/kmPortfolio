package com.km.kmportfolio.web.data;

import lombok.Data;

@Data
public class MailDto {
    private String email;
    private String title;
    private String message;
    private String username;
}
