package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gabinetynagodziny.officesforrent.entity.*;
import pl.gabinetynagodziny.officesforrent.service.*;
import pl.gabinetynagodziny.officesforrent.util.Constans;
import pl.gabinetynagodziny.officesforrent.util.FileUploadUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final DetailService detailService;
    private final ScheduleService scheduleService;
    private final DictionaryService dictionaryService;
    private final UserService userService;

    public OfficeController(OfficeService officeService, DetailService detailService
            , DictionaryService dictionaryService, ScheduleService scheduleService
            ,UserService userService){
        this.officeService = officeService;
        this.detailService = detailService;
        this.dictionaryService = dictionaryService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @GetMapping
    public String offices(Model model){
        List<Office> offices = officeService.findAllAccepted();
        for (Office office : offices){
            office.refreshOfficeScheduleMap();
        }
        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(Constans.PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("purposes", purposes);
        return "offices";
    }

    @GetMapping("/my")
    public String officesMy(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";}

        User loggedUser = optionalUser.get();

        List<Office> offices = officeService.findByUserId(loggedUser.getUserId());

        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(Constans.PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("purposes", purposes);
        return "officesmy";
    }

    @GetMapping("/add")
    public String addOffice(Model model){
        Office office = new Office();
        model.addAttribute("office", office);

        List<Detail> details = detailService.findAll();
        List<Detail> purposesL = details.stream()
                .filter(s -> s.getDetailType().equals(Constans.PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("purposes", purposesL);

        return "addoffice";
    }

    @PostMapping("/add")
    public String addOfficePost(@Valid Office office, BindingResult result, Model model, @RequestParam("image") MultipartFile multipartFile
            , @RequestParam("purposeId") Long purposeId) throws IOException {
        if(result.hasErrors()){
            return "addoffice";
        }
        System.out.println("purposeId: "+ purposeId);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        office.setPhotos(fileName);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> optionalUser = userService.findByUsername(currentPrincipalName);
        if(optionalUser.isEmpty()){
            return "error";}

        User loggedUser = optionalUser.get();
        office.setUserId(loggedUser.getUserId());

        Optional<Detail> optionalDetail = detailService.findByDetailId(purposeId);
        if(optionalDetail.isEmpty()){
            return "error";}

        if(office.getDetails() == null){
            List<Detail> details = new ArrayList<>();
            office.setDetails(details);
        }
            office.getDetails().add(optionalDetail.get());

        Office officeSaved = officeService.mergeOffice(office);
        String uploadDir = "src/main/resources/static/img/" + officeSaved.getOfficeId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/offices/"+ officeSaved.getOfficeId();
    }

    @GetMapping("/{id}")
    public String editOffice(Model model, @PathVariable("id") Long id){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
            return "error";
        }
        model.addAttribute("office", optionalOffice.get());
        return "addoffice";
    }

    @GetMapping("/search")
    public String searchOffice(Model model, @RequestParam(required = false) Float priceMin
            , @RequestParam(required = false) Float priceMax
            , @RequestParam(required = false) Integer capacityMin
            , @RequestParam(required = false) Long purposeId){


        List<Office> offices =
                officeService.findSearch(priceMin, priceMax, capacityMin, purposeId);
        for (Office office : offices){
            office.refreshOfficeScheduleMap();
        }
        List<Detail> details = detailService.findAll();
        List<Detail> furnishingsL = details.stream()
                .filter(s -> s.getDetailType().equals(Constans.FURNISHINGS))
                .collect(Collectors.toList());
        List<Detail> purposesL = details.stream()
                .filter(s -> s.getDetailType().equals(Constans.PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishingsL);
        model.addAttribute("purposes", purposesL);
        model.addAttribute("offices", offices);

        return "offices";
    }

    @PostMapping("/{id}/accept")
    public String acceptOffice(@PathVariable("id") Long id){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
            return "error";
        }
        Office officeToAccept = optionalOffice.get();
        officeToAccept.doAcceptance();

        officeService.mergeOffice(officeToAccept);

        return "redirect:/notifications";
    }

}
