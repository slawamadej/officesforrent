package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.repository.CrudRepository;
import pl.gabinetynagodziny.officesforrent.entity.Unit;

public interface UnitRepository extends CrudRepository<Unit, Long> {
}
