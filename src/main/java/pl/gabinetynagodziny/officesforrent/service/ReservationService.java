package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation mergeReservation(Reservation reservation);

    List<Reservation> findAll();

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByOfficeId(Long officeId);

    List<Reservation> findAllAccepted();

    List<Reservation> findAllByUserIdNotAccepted(Long userId);

    Optional<Reservation> findById(Long reservationId);
}
