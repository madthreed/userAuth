package com.madthreed.userAuth.repos;

import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    Token findByUser(User user);
}
