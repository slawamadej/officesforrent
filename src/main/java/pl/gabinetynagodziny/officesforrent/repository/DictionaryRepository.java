package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;

@Repository
public interface DictionaryRepository extends JpaRepository<DictionaryApp, Long> {

    @Query(value = "SELECT d.description FROM dictionaries d WHERE d.code = :code", nativeQuery = true)
    String findDescriptionByCode(@Param("code") String code);
}
