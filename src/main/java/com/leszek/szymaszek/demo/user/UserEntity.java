package com.leszek.szymaszek.demo.user;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
class UserEntity {

    private UserEntity() {}

    private String firstName;
    private String lastName;
    private int age;
    private BigDecimal height;
    private Instant createdDate;


    static UserEntity from(SaveUserCommand command) {
        final var user = new UserEntity();
        user.firstName = command.firstName();
        user.lastName = command.lastName();
        user.age = command.age();
        user.height = command.height();
        user.createdDate = Instant.now();
        return user;
    }
}
