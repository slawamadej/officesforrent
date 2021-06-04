package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.repository.OfficeRepository;
import pl.gabinetynagodziny.officesforrent.service.DetailService;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final DetailService detailService;
    public static final String FURNISHINGS = "FURNISHINGS";
    public static final String PURPOSES = "PURPOSES";

    public OfficeController(OfficeService officeService, DetailService detailService){
        this.officeService = officeService;
        this.detailService = detailService;
    }

    @GetMapping
    public String offices(Model model){
        List<Office> offices = officeService.findAll();
        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> furnishings = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
              //  .sorted()
                .collect(Collectors.toList());
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
            //    .sorted()
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishings);
        model.addAttribute("purposes", purposes);
        return "offices";
    }

    @GetMapping("/add")
    public String addOffice(Model model){
        Office office = new Office();
        model.addAttribute("office", office);
        return "addoffice";
    }

    @PostMapping("/add")
    public String addOfficePost(@Valid Office office, BindingResult result, Model model, HttpSession httpSession){
        if(result.hasErrors()){
            return "addoffice";
        }

        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        office.setUserId(sessionUserId);

        System.out.println("office/add sessionUserId: " + sessionUserId);

        Office officeSaved = officeService.mergeOffice(office);
        return "redirect:/offices/"+ officeSaved.getOfficeId();
    }

    @GetMapping("/{id}")
    public String editOffice(Model model, @PathVariable("id") Long id){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
        }
        model.addAttribute("office", optionalOffice.get());
        return "addoffice";
    }

    @GetMapping("/search")
    public String searchOffice(Model model, @RequestParam(required = false) Float priceMin
            , @RequestParam(required = false) Float priceMax
            , @RequestParam(required = false) Integer capacityMin){

        List<Office> offices =
                officeService.findSearch(priceMin, priceMax, capacityMin);
        System.out.println("ile gabinetow: " + offices.size());
        List<Detail> details = detailService.findAll();
        List<Detail> furnishings = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
                .sorted()
                .collect(Collectors.toList());
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishings);
        model.addAttribute("purposes", purposes);
        model.addAttribute("offices", offices);

        return "offices";
    }


}
