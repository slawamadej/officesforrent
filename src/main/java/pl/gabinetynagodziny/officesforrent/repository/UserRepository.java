package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.repository.CrudRepository;
import pl.gabinetynagodziny.officesforrent.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    //interface bez public
    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);
}
