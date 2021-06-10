package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.User;

import java.util.Optional;

public interface UserService {

    User signUpUser(User user);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUsername(String username);
}
