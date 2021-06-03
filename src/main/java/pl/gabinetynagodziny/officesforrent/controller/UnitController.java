package pl.gabinetynagodziny.officesforrent.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;

    public UnitController(UnitService unitService, UnitRepository unitRepository, ModelMapper modelMapper){
        this.unitService = unitService;
        this.unitRepository = unitRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addUnit(Model model){
        Unit unit = new Unit();
        model.addAttribute(unit);
        return "addunit";
    }

    @ResponseBody
    @PostMapping("/add")
    public String addUnitPost(@Valid Unit unit, BindingResult bindingResult, HttpSession httpSession){
        //userId z sesji musze jakos wyciagnac
        //sprawdzic czy istnieje id, jak tak to nie dodawanie nowego tylko update

        if (bindingResult.hasErrors()){
            return "addunit";
        }

        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        unit.setUserId(sessionUserId);

        //jesli nie ma unitId!!!
       /* Unit unitToAdd = new Unit();
        unitToAdd.builder().consent(consent)
                .type(type)
                .firstName(firstName)
                .name(name)
                .webpage(webpage)
                .email(email)
                .phoneNumber(phoneNumber)
                .taxNumber(taxNumber)
                .build();*/

        //Unit unitToAdd = modelMapper.map(unitDto, Unit.class);

        unitService.mergeUnit(unit);

        //User userToSignUp = new User(username, password, email);
        //signUpService.signUpUser(userToSignUp);
        //modelAndView.setViewName("redirect:/login");

        return "teoretycznie dodano profile";
    }

}
