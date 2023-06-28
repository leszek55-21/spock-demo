package com.leszek.szymaszek.demo.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.lang.String.format;

class SaveUserCommandValidatorTest {

    private final SaveUserCommandValidator saveUserCommandValidator = new SaveUserCommandValidator();

    private static Stream<Arguments> validFirstNameLastNameAndAge() {
        return Stream.of(
                Arguments.of("a".repeat(10), "a".repeat(2), 55 ),
                Arguments.of("a".repeat(3), "a".repeat(33), 2),
                Arguments.of("a".repeat(4), "a".repeat(24), 3));
    }

    @ParameterizedTest
    @MethodSource("validFirstNameLastNameAndAge")
    void shouldPassValidation(String firstName, String lastName, int age) {
        //given
        final var saveUserCommand = new SaveUserCommand(firstName, lastName, age, new BigDecimal("199.99"));

        //expect
        Assertions.assertDoesNotThrow(() -> saveUserCommandValidator.validate(saveUserCommand));
    }

    private static Stream<Arguments> invalidFirstNameLastNameAndAge() {
        return Stream.of(
                Arguments.of("a".repeat(10), "a".repeat(5), 50 ),
                Arguments.of("a".repeat(3), "a".repeat(33), 1));
    }

    @ParameterizedTest
    @MethodSource("invalidFirstNameLastNameAndAge")
    void shouldFailValidation(String firstName, String lastName, int age) {
        //given
        final var saveUserCommand = new SaveUserCommand(firstName, lastName, age, new BigDecimal("199.99"));

        //expect
        final var exception = Assertions.assertThrows(ValidationException.class, () -> saveUserCommandValidator.validate(saveUserCommand));

        //and
        Assertions.assertEquals(exception.getMessage(), format("Product of first name length, last name length summed with age cannot be %s", 100));
    }

}