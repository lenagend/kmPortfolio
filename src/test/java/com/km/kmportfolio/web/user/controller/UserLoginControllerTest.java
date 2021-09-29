package com.km.kmportfolio.web.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception{
        mockMvc.perform(get("/login"))

                .andExpect(status().isOk())

                .andExpect(view().name("user/login/form"))

                .andExpect(content().string(containsString("로그인")));
    }

    @Test
    void register() throws Exception{
        mockMvc.perform(get("/login/register"))

                .andExpect(status().isOk())

                .andExpect(view().name("user/register/form"))

                .andExpect(content().string(containsString("회원가입")));
    }
}