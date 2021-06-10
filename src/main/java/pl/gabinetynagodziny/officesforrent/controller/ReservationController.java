package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Reservation;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;
import pl.gabinetynagodziny.officesforrent.service.ReservationService;
import pl.gabinetynagodziny.officesforrent.service.UserService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {

    private final OfficeService officeService;
    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(OfficeService officeService, ReservationService reservationService, UserService userService){
        this.officeService = officeService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/offices/{id}/reservation/add/{date}/{hour}")
    public String addReservation(@PathVariable("id") Long id, @PathVariable("date") String date, @PathVariable("hour") String hour, HttpSession httpSession){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
        }
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        sessionUserId = 1L;
        Optional<User> optionalUser = userService.findByUserId(sessionUserId);
        if(optionalUser.isEmpty()){
        }

        Reservation reservation = new Reservation(optionalOffice.get(), optionalUser.get(), date, hour);

        reservationService.mergeReservation(reservation);

        return "redirect:/reservations/my";

    }

    @GetMapping("/reservations/my")
    public String myReservations(Model model, HttpSession httpSession){
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        sessionUserId = 1L;

        List<Reservation> reservations = reservationService.findByUserId(sessionUserId);
        model.addAttribute("reservations", reservations);

        return "reservations";
    }
}
