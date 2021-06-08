package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;

@Repository
public interface DictionaryAppRepository extends JpaRepository<DictionaryApp, Long> {

}
