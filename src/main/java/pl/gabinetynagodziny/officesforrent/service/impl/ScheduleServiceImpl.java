package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.entity.Schedule;
import pl.gabinetynagodziny.officesforrent.repository.ScheduleRepository;
import pl.gabinetynagodziny.officesforrent.service.ScheduleService;

import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> findByOffice(Office office) {
        return scheduleRepository.findByOffice(office);
    }

    @Override
    public Schedule mergeSchedule(Schedule schedule) {
        Schedule scheduleSaved = scheduleRepository.save(schedule);
        return scheduleSaved;
    }
}
