package pl.gabinetynagodziny.officesforrent.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.gabinetynagodziny.officesforrent.dto.UnitDto;
import pl.gabinetynagodziny.officesforrent.entity.Unit;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UnitRepository;
import pl.gabinetynagodziny.officesforrent.service.UnitService;
import pl.gabinetynagodziny.officesforrent.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UnitController(UnitService unitService, UnitRepository unitRepository, ModelMapper modelMapper
            , UserService userService){
        this.unitService = unitService;
        this.unitRepository = unitRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addUnit(Model model){
        Unit unit = new Unit();
        model.addAttribute("unit", unit);

        Map<String, String> unitType = new HashMap<>();
        unitType.put("P", "Person");
        unitType.put("C", "Company");

        model.addAttribute("unitType", unitType);
        return "addunit";
    }

    @PostMapping("/add")
    public String addUnitPost(@Valid Unit unit, BindingResult bindingResult, HttpSession httpSession){
        if (bindingResult.hasErrors() || unit.getConsent() == false){
            return "addunit";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";}

        User loggedUser = optionalUser.get();
        unit.setUser(loggedUser);

        Unit unitsaved = unitService.mergeUnit(unit);
        loggedUser.setUnit(unitsaved);

        userService.mergeUser(loggedUser);

        return "redirect:/offices";
    }

}
