package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.entity.User;

import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Long> {

    Optional<Detail> findByDetailId(Long detailId);
}
