package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import pl.gabinetynagodziny.officesforrent.service.ScheduleService;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
/*
    @GetMapping("/sched/offices/{id}/schedule")
    public String schedule(Model model, @PathVariable("id") Long id){
        return "schedules";
    }

    @PostMapping("/offices/{id}/schedule/add")
    public String scheduleAdd(Model model, @PathVariable("id") Long id){
        List<Schedule> schedules = scheduleService.findByOfficeId(id);

        model.addAttribute("schedule", schedules);
        return "addschedule";
    }
*/
}
