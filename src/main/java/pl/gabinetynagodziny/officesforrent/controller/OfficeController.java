package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.repository.OfficeRepository;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService){
        this.officeService = officeService;
    }

    @GetMapping
    public String offices(Model model){
        List<Office> offices =
                officeService.findAll();
        model.addAttribute("offices", offices);
        return "offices";
    }

    @GetMapping("/add")
    public String addOffice(Model model){
        Office office = new Office();
        model.addAttribute("office", office);
        return "addoffice";
    }

    @ResponseBody
    @PostMapping("/add")
    public String addOfficePost(@Valid Office office, BindingResult result, Model model, HttpSession httpSession){
        if(result.hasErrors()){
            return "jakistam blad";
        }

        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        office.setUserId(sessionUserId);

        Office officeSaved = officeService.mergeOffice(office);
        return "powodzenie";//"/" + officeSaved.getOfficeId();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public String editOffice(Model model){
        return "costam";
    }


}
