package com.leszek.szymaszek.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
class UserRepository {

    UserEntity save(UserEntity user) {
        log.debug("saving new User: {}", user);
        return user;
    }
}
