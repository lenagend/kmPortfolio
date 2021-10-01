package com.km.kmportfolio.config.security;

import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailService implements UserDetailsService {
    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found"
        );
    }

}
