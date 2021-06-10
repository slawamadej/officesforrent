package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Schedule;
import pl.gabinetynagodziny.officesforrent.service.DictionaryService;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;
import pl.gabinetynagodziny.officesforrent.service.ScheduleService;
import pl.gabinetynagodziny.officesforrent.util.Constans;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final OfficeService officeService;
    private final DictionaryService dictionaryService;

    public ScheduleController(ScheduleService scheduleService, OfficeService officeService, DictionaryService dictionaryService) {
        this.scheduleService = scheduleService;
        this.officeService = officeService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/offices/{id}/schedule")
    public String schedule(Model model, @PathVariable("id") Long id){
        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        List<DictionaryApp> dictionaries = dictionaryService.findAll();
        List<DictionaryApp> scheduleTypes = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(Constans.SCHEDULE_TYPE))
                .collect(Collectors.toList());

        model.addAttribute("scheduleTypes", scheduleTypes);

        List<DictionaryApp> dayOfTheWeek = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(Constans.DAYS_OF_WEEK))
                .collect(Collectors.toList());

        model.addAttribute("dayOfTheWeek", dayOfTheWeek);

        if(optionalOffice.isEmpty()){
        }

        Office office = optionalOffice.get();
        office.refreshOfficeScheduleMap();
        model.addAttribute("office", office);
        return "schedules";
    }

    @GetMapping("/offices/{id}/schedule/add")
    public String scheduleAdd(Model model, @PathVariable("id") Long id){
        Schedule schedule = new Schedule();
        List<DictionaryApp> dictionaries = dictionaryService.findAll();
        List<DictionaryApp> scheduleTypes = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(Constans.SCHEDULE_TYPE))
                .collect(Collectors.toList());
        List<DictionaryApp> days = dictionaries.stream()
                .filter(s -> s.getDictCode().equals(Constans.DAYS_OF_WEEK))
                .collect(Collectors.toList());

        model.addAttribute("scheduleTypes", scheduleTypes);
        model.addAttribute("days", days);
        model.addAttribute("schedule", schedule);
        model.addAttribute("officeId", id);
        return "addschedule";
    }

    @PostMapping("/offices/{id}/schedule/add")
    public String scheduleAddPost(Model model, @PathVariable("id") Long id, Schedule schedule){
        System.out.println("START TIME" + schedule.getStartTime());

        Optional<Office> optionalOffice = officeService.findByOfficeId(id);
        if(optionalOffice.isEmpty()){
        }
        Office office = optionalOffice.get();
        schedule.setOffice(office);
        Schedule scheduleSaved = scheduleService.mergeSchedule(schedule);

        office.addSchedule(scheduleSaved);
        office.refreshOfficeScheduleMap();

        return "redirect:/offices/" + id + "/schedule";

    }

}
