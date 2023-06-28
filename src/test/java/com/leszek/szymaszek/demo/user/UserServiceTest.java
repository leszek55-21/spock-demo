package com.leszek.szymaszek.demo.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {

    private SaveUserCommandValidator userValidator;
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setup() {
        userValidator = Mockito.mock(SaveUserCommandValidator.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userValidator, userRepository);
    }

    private static Stream<Arguments> firstNameWithLastName() {
        return Stream.of(
                Arguments.of("John", "Doe"),
                Arguments.of("Tom", "Jones"));
    }

    @ParameterizedTest
    @MethodSource("firstNameWithLastName")
    void shouldSaveUserAndReturnSaveResult(String fName, String lName) {
        //given
        final var givenAge = 48;
        final var givenHeight = new BigDecimal("1.85");
        final var saveUserCommand = new SaveUserCommand(fName, lName, givenAge, givenHeight);
        final var user = createUser(fName, lName, givenAge, givenHeight);
        BDDMockito.given(userRepository.save(any(UserEntity.class))).willReturn(user);

        //when
        final var result = userService.saveUser(saveUserCommand);

        //then
        Assertions.assertThat(result.firstName()).isEqualTo(saveUserCommand.firstName());
        Assertions.assertThat(result.lastName()).isEqualTo(saveUserCommand.lastName());
        Assertions.assertThat(result.age()).isEqualTo(saveUserCommand.age());
        Assertions.assertThat(result.height()).isEqualTo(saveUserCommand.height());
        Assertions.assertThat(result.createdDate()).isNotNull();

        //and
        BDDMockito.verify(userValidator).validate(saveUserCommand);
    }

    private UserEntity createUser(String firstName, String lastName, int age, BigDecimal height) {
        final var user = Mockito.mock(UserEntity.class);
        BDDMockito.given(user.getFirstName()).willReturn(firstName);
        BDDMockito.given(user.getLastName()).willReturn(lastName);
        BDDMockito.given(user.getAge()).willReturn(age);
        BDDMockito.given(user.getHeight()).willReturn(height);
        BDDMockito.given(user.getCreatedDate()).willReturn(Instant.now());
        return user;
    }

    @Test
    void shouldSaveUser() {
        //given
        final var userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        final var givenAge = 48;
        final var givenHeight = new BigDecimal("1.85");
        final var givenFirstName = "John";
        final var givenLastName = "Doe";
        final var saveUserCommand = new SaveUserCommand(givenFirstName, givenLastName, givenAge, givenHeight);

        //when
        userService.saveUser2(saveUserCommand);

        //then
        BDDMockito.verify(userRepository).save(userCaptor.capture());

        //and
        final var savedUser = userCaptor.getValue();
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo(givenFirstName);
        Assertions.assertThat(savedUser.getLastName()).isEqualTo(givenLastName);
        Assertions.assertThat(savedUser.getAge()).isEqualTo(givenAge);
        Assertions.assertThat(savedUser.getHeight()).isEqualTo(givenHeight);
        Assertions.assertThat(savedUser.getCreatedDate()).isNotNull();
    }
}