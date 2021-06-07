package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query(value = "SELECT o.* FROM offices o WHERE o.price BETWEEN IFNULL(:priceMin, o.price) AND " +
            "IFNULL(:priceMax, o.price) AND o.capacity >= IFNULL(:capacityMin,0)", nativeQuery = true)
    List<Office> findSearch(@Param("priceMin") Float priceMin, @Param("priceMax") Float priceMax, @Param("capacityMin") Integer capacityMin);

    List<Office> findByUserId(Long userId);
}
