package com.leszek.szymaszek.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SaveUserCommandValidator userValidator;
    private final UserRepository userRepository;

    UserView saveUser(SaveUserCommand command) {
        userValidator.validate(command);
        final var toSave = UserEntity.from(command);
        final var saved = userRepository.save(toSave);
        return UserView.from(saved);
    }

    void saveUser2(SaveUserCommand command) {
        userValidator.validate(command);
        final var toSave = UserEntity.from(command);
        userRepository.save(toSave);
    }
}
