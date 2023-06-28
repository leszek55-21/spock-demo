package com.leszek.szymaszek.demo.user;

import org.springframework.stereotype.Service;

import static java.lang.String.*;

@Service
class SaveUserCommandValidator {

    private static final int FORBIDDEN_RESULT = 100;

    public void validate(SaveUserCommand command) {
        final var firstName = command.firstName();
        final var lastName = command.lastName();
        final var age = command.age();
        if (firstName.length() * lastName.length() + age == FORBIDDEN_RESULT) {
            throw new ValidationException(format("Product of first name length, last name length summed with age cannot be %s", FORBIDDEN_RESULT));
        }
    }
}
