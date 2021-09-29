package com.km.kmportfolio.web.data;

import com.km.kmportfolio.web.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
