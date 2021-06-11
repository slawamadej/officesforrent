package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.ArrayList;
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

    @PostMapping("/offices/{id}/reservation/add/{date}/{hour}")
    public String addReservation(@PathVariable("id") Long id, @PathVariable("date") String date, @PathVariable("hour") String hour, HttpSession httpSession){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
            return "error";
        }
        Office office = optionalOffice.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";}

        User loggedUser = optionalUser.get();

        Reservation reservation = new Reservation(office, loggedUser, date, hour);

        reservationService.mergeReservation(reservation);

        if(office.getReservations() == null){
            List<Reservation> reservationList  = new ArrayList<>();
            office.setReservations(reservationList);
        }

        office.getReservations().add(reservation);

        return "redirect:/reservations/my";

    }

    @GetMapping("/reservations/my")
    public String myReservations(Model model, HttpSession httpSession){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";}

        User loggedUser = optionalUser.get();

        List<Reservation> reservations = reservationService.findByUserId(loggedUser.getUserId());
        model.addAttribute("reservations", reservations);

        return "reservations";
    }

    @PostMapping("/reservations/{id}/accept")
    public String acceptOffice(@PathVariable("id") Long id){
        Optional<Reservation> optionalReservation = reservationService.findById(id);
        if(optionalReservation.isEmpty()){
            return "error";
        }
        Reservation reservationToAccept = optionalReservation.get();
        reservationToAccept.doAcceptance();

        reservationService.mergeReservation(reservationToAccept);

        return "redirect:/notifications";
    }
}
