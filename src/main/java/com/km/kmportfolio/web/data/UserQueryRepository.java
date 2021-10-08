package com.km.kmportfolio.web.data;

import com.km.kmportfolio.web.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryRepository extends JpaRepository<User, Long> {
    Page<User> findAllByUsernameContainingIgnoreCase(Pageable var1, String username);
    Page<User> findAllByRegidateContainingIgnoreCase(Pageable var1, String regidate);
    Page<User> findAllByAuthority(Pageable var1, String authority);
    Page<User> findAllByEnabled(Pageable var1, int enabled);
}
