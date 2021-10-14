package com.km.kmportfolio.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailService;


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/write")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                .permitAll()

                .and()
                .oauth2Login()
                .loginPage("/login")

                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomSuccessHandler())
                .defaultSuccessUrl("/")


                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth
               .userDetailsService(userDetailService)
               .passwordEncoder(encoder());
    }
}
