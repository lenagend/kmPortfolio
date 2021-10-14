package com.km.kmportfolio.config.security;

import com.km.kmportfolio.web.data.UserRepository;
import com.km.kmportfolio.web.entity.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class UserRepositoryUserDetailService implements UserDetailsService {
    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (!user.isAccountNonLocked()){
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal.setTime(new Date());
            DateFormat df = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
            Date stopDate = df.parse(user.getLocked());
            cal2.setTime(stopDate);
            int result = cal.compareTo(cal2);
            if (result >= 0){
                user.setLocked("nonLocked");
                userRepo.save(user);
            }
        }
        if (user != null){
            return user;
        }

        throw new UsernameNotFoundException(
                "User '" + username + "' not found"
        );
    }

}
