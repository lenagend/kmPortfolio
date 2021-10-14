package com.km.kmportfolio.config.security;

import com.km.kmportfolio.web.entity.User;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

       User user = (User) authentication.getPrincipal();
       if (!user.isAccountNonLocked()){
           String limit = user.getLocked();
           throw new LockedException(limit + "까지 계정이 정지되었습니다. 관리자에게 문의해주세요");
       }
    }
}
