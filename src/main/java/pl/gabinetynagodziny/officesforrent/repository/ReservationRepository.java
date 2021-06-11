package pl.gabinetynagodziny.officesforrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r.* FROM reservations r WHERE r.accepted = true", nativeQuery = true)
    List<Reservation> findAllAccepted();

    @Query(value = "SELECT r.* FROM reservations r " +
            "JOIN offices o ON o.office_id = r.office_office_id " +
            "WHERE r.accepted = false " +
            "AND o.user_id = :userId", nativeQuery = true)
    List<Reservation> findAllByUserIdNotAccepted(@Param("userId") Long userId);

    @Query(value="SELECT r.* FROM reservations r WHERE r.user_user_id = :userId", nativeQuery = true)
    List<Reservation> findByUserId(@Param("userId") Long userId);

    @Query(value="SELECT r.* FROM reservations r WHERE r.office_office_id = :officeId", nativeQuery = true)
    List<Reservation> findByOfficeId(@Param("officeId") Long userId);
}
