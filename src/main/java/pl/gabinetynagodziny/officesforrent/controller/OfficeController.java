package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Schedule;
import pl.gabinetynagodziny.officesforrent.service.DetailService;
import pl.gabinetynagodziny.officesforrent.service.DictionaryService;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;
import pl.gabinetynagodziny.officesforrent.service.ScheduleService;
import pl.gabinetynagodziny.officesforrent.util.Constans;
import pl.gabinetynagodziny.officesforrent.util.FileUploadUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public static final String FURNISHINGS = "FURNISHINGS";
    public static final String PURPOSES = "PURPOSES";
    public static final String SCHEDULE_TYPE = "SCHEDULE_TYPE";
    public static final String DAYS_OF_WEEK = "DAYS_OF_WEEK";

    public OfficeController(OfficeService officeService, DetailService detailService
            , DictionaryService dictionaryService, ScheduleService scheduleService){
        this.officeService = officeService;
        this.detailService = detailService;
        this.dictionaryService = dictionaryService;
        this.scheduleService = scheduleService;
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
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("purposes", purposes);
        return "offices";
    }

    @GetMapping("/my")
    public String officesMy(Model model, HttpSession httpSession){
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        List<Office> offices = officeService.findByUserId(sessionUserId);
        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("purposes", purposes);
        return "officesmy";
    }

    @GetMapping("/add")
    public String addOffice(Model model){
        Office office = new Office();
        model.addAttribute("office", office);
        return "addoffice";
    }

    @PostMapping("/add")
    public String addOfficePost(@Valid Office office, BindingResult result, Model model, HttpSession httpSession
    ,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(result.hasErrors()){
            return "addoffice";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        office.setPhotos(fileName);

        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        office.setUserId(sessionUserId);

        System.out.println("office/add sessionUserId: " + sessionUserId);

        Office officeSaved = officeService.mergeOffice(office);

        //String uploadDir = "user-photos/" + officeSaved.getOfficeId();
        String uploadDir = "src/main/resources/static/img/" + officeSaved.getOfficeId();
        System.out.println("uploadDir" + uploadDir);
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
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
            , @RequestParam(required = false) Integer capacityMin
            , @RequestParam(required = false) Long purposeId){


        List<Office> offices =
                officeService.findSearch(priceMin, priceMax, capacityMin, purposeId);
        for (Office office : offices){
            office.refreshOfficeScheduleMap();
        }
        List<Detail> details = detailService.findAll();
        List<Detail> furnishingsL = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
                .collect(Collectors.toList());
        List<Detail> purposesL = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishingsL);
        model.addAttribute("purposes", purposesL);
        model.addAttribute("offices", offices);

        return "offices";
    }

}
