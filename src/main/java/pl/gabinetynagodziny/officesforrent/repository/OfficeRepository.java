package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.Office;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query(value = "SELECT o.* FROM offices o " +
            "LEFT JOIN office_details p ON o.office_id = p.office_id AND :purposeId IS NOT NULL " +
            "WHERE o.price BETWEEN IFNULL(:priceMin, o.price) AND " +
            "IFNULL(:priceMax, o.price) AND o.capacity >= IFNULL(:capacityMin,0) " +
            "AND IFNULL(p.detail_id, -1) = IFNULL(:purposeId, -1)", nativeQuery = true)
    List<Office> findSearch(@Param("priceMin") Float priceMin, @Param("priceMax") Float priceMax
                            , @Param("capacityMin") Integer capacityMin, @Param("purposeId") Long purposeId);

    List<Office> findByUserId(Long userId);

    @Query(value = "SELECT o.* FROM offices o WHERE o.accepted = true", nativeQuery = true)
    List<Office> findAllAccepted();

    @Query(value = "SELECT o.* FROM offices o WHERE o.accepted = false", nativeQuery = true)
    List<Office> findAllNotAccepted();
}
