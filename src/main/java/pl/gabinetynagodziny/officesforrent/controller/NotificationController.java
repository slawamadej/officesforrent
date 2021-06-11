package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gabinetynagodziny.officesforrent.entity.*;
import pl.gabinetynagodziny.officesforrent.service.DetailService;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;
import pl.gabinetynagodziny.officesforrent.service.ReservationService;
import pl.gabinetynagodziny.officesforrent.service.UserService;
import pl.gabinetynagodziny.officesforrent.util.Constans;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final UserService userService;
    private final OfficeService officeService;
    private final DetailService detailService;
    private final ReservationService reservationService;

    public NotificationController(UserService userService, OfficeService officeService
            , DetailService detailService, ReservationService reservationService){
        this.userService = userService;
        this.officeService = officeService;
        this.detailService = detailService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String notification(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";
        } else {
            User loggedUser = optionalUser.get();
            if(loggedUser.getRoles().stream()
                    .filter(role -> Constans.ROLE_ADMIN.equals(role.getName()))
                    .collect(Collectors.toList()).size()>0) {
                //wziac gabinety do akceptacji
                    List<Office> offices = officeService.findAllNotAccepted();
                    model.addAttribute("offices", offices);
                    List<Detail> details = detailService.findAll();
                    List<Detail> purposes = details.stream()
                            .filter(s -> s.getDetailType().equals(Constans.PURPOSES))
                            .collect(Collectors.toList());
                    model.addAttribute("purposes", purposes);

                return "notificationsAdmin";
            }
                List<Reservation> reservationNotAccepted = reservationService.findAllByUserIdNotAccepted(loggedUser.getUserId());
                model.addAttribute("reservations", reservationNotAccepted);

                return "notificationsUser";
            }
        }
    }

