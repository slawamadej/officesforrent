package pl.gabinetynagodziny.officesforrent.util;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ScheduleUtil {
    private LocalDate date;
    private LinkedHashSet<String> hours;//to bez powtorzen chce i w kolejnosci

    public ScheduleUtil(LocalDate date, LinkedHashSet<String> hours){
        this.date = date;
        this.hours = hours;
    }

}
