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
import pl.gabinetynagodziny.officesforrent.service.DictionaryAppService;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;
import pl.gabinetynagodziny.officesforrent.service.ScheduleService;
import pl.gabinetynagodziny.officesforrent.util.FileUploadUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;
    private final DetailService detailService;
    private final ScheduleService scheduleService;
    private final DictionaryAppService dictionaryAppService;

    public static final String FURNISHINGS = "FURNISHINGS";
    public static final String PURPOSES = "PURPOSES";
    public static final String SCHEDULE_TYPE = "SCHEDULE_TYPE";
    public static final String DAYS_OF_WEEK = "DAYS_OF_WEEK";

    public OfficeController(OfficeService officeService, DetailService detailService
            , DictionaryAppService dictionaryAppService, ScheduleService scheduleService){
        this.officeService = officeService;
        this.detailService = detailService;
        this.dictionaryAppService = dictionaryAppService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public String offices(Model model){
        List<Office> offices = officeService.findAll();
        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> furnishings = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
                .collect(Collectors.toList());
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishings);
        model.addAttribute("purposes", purposes);
        return "offices";
    }

    @GetMapping("/my")
    public String officesMy(Model model, HttpSession httpSession){
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        List<Office> offices = officeService.findByUserId(sessionUserId);
        model.addAttribute("offices", offices);
        List<Detail> details = detailService.findAll();
        List<Detail> furnishings = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
                .collect(Collectors.toList());
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
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
    public String addOfficePost(@Valid Office office, BindingResult result, Model model, HttpSession httpSession
    ,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(result.hasErrors()){
            return "addoffice";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println("Zdjecie nazwa: "+fileName);
        office.setPhotos(fileName);

        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        office.setUserId(sessionUserId);

        System.out.println("office/add sessionUserId: " + sessionUserId);

        Office officeSaved = officeService.mergeOffice(office);

        String uploadDir = "user-photos/" + officeSaved.getOfficeId();
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
            , @RequestParam(required = false) Integer capacityMin){

        List<Office> offices =
                officeService.findSearch(priceMin, priceMax, capacityMin);
        List<Detail> details = detailService.findAll();
        List<Detail> furnishings = details.stream()
                .filter(s -> s.getDetailType().equals(FURNISHINGS))
                .collect(Collectors.toList());
        List<Detail> purposes = details.stream()
                .filter(s -> s.getDetailType().equals(PURPOSES))
                .collect(Collectors.toList());
        model.addAttribute("furnishings", furnishings);
        model.addAttribute("purposes", purposes);
        model.addAttribute("offices", offices);

        return "offices";
    }

    //SCHEDULE
    @GetMapping("/{id}/schedule")
    public String schedule(Model model, @PathVariable("id") Long id){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        List<DictionaryApp> dictionaries = dictionaryAppService.findAll();
        List<DictionaryApp> scheduleTypes = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(SCHEDULE_TYPE))
                .collect(Collectors.toList());

        model.addAttribute("scheduleTypes", scheduleTypes);

        if(optionalOffice.isEmpty()){
        }
        model.addAttribute("office", optionalOffice.get());
        return "schedules";
    }

    @GetMapping("/{id}/schedule/add")
    public String scheduleAdd(Model model, @PathVariable("id") Long id){
        Schedule schedule = new Schedule();
        List<DictionaryApp> dictionaries = dictionaryAppService.findAll();
        List<DictionaryApp> scheduleTypes = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(SCHEDULE_TYPE))
                .collect(Collectors.toList());
        List<DictionaryApp> days = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(DAYS_OF_WEEK))
                .collect(Collectors.toList());

        model.addAttribute("scheduleTypes", scheduleTypes);
        model.addAttribute("days", days);
        model.addAttribute("schedule", schedule);
        model.addAttribute("officeId", id);
        return "addschedule";
    }

    @PostMapping("/{id}/schedule/add")
    public String scheduleAddPost(Model model, @PathVariable("id") Long id, @Valid Schedule schedule){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
        }
        Office office = optionalOffice.get();
        schedule.setOffice(office);
        System.out.println("koncowa data:" + schedule.getEndTime());
        Schedule scheduleSaved = scheduleService.mergeSchedule(schedule);


        office.addSchedule(scheduleSaved);

        return "redirect:/offices/" + id + "/schedule";

    }

}
