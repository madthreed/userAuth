package com.madthreed.userAuth.repos;

import com.madthreed.userAuth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

//    User findByActivationCode(String code);
}
