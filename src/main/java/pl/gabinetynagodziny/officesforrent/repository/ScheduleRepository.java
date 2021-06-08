package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByOffice(Office office);


}
