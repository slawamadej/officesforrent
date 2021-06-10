package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import pl.gabinetynagodziny.officesforrent.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //interface bez public
    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);

}
