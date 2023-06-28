package com.leszek.szymaszek.demo.user;

import java.math.BigDecimal;
import java.time.Instant;

public record UserView(String firstName, String lastName, int age, BigDecimal height, Instant createdDate) {

    public static UserView from(UserEntity user) {
        return new UserView(user.getFirstName(), user.getLastName(), user.getAge(), user.getHeight(), user.getCreatedDate());
    }
}
