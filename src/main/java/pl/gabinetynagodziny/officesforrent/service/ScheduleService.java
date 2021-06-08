package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    List<Schedule> findByOffice(Office office);

    Schedule mergeSchedule(Schedule schedule);
}
