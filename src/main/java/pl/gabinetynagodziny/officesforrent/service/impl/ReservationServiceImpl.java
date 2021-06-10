package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gabinetynagodziny.officesforrent.entity.Reservation;
import pl.gabinetynagodziny.officesforrent.repository.ReservationRepository;
import pl.gabinetynagodziny.officesforrent.service.ReservationService;

import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation mergeReservation(Reservation reservation) {
        Reservation reservationSaved = reservationRepository.save(reservation);
        return reservationSaved;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> findByOfficeId(Long officeId) {
        return reservationRepository.findByOfficeId(officeId);
    }

    @Override
    public List<Reservation> findAllAccepted() {
        return reservationRepository.findAllAccepted();
    }

    @Override
    public List<Reservation> findAllNotAccepted() {
        return reservationRepository.findAllNotAccepted();
    }
}
