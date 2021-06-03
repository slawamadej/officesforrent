package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gabinetynagodziny.officesforrent.entity.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
