package pl.gabinetynagodziny.officesforrent.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.gabinetynagodziny.officesforrent.util.Constans;
import pl.gabinetynagodziny.officesforrent.util.ScheduleUtil;

import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private String scheduleType;//WORKING_DAY, DAY_OF_WEEK, DATA

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endDate;

    private Integer startTime;
    private Integer endTime;

    private String dayOfTheWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

   /*private List<ScheduleUtil> scheduleUtilList;

    public Schedule(String scheduleType, String dayOfTheWeek, LocalDate startDate, LocalDate endDate, Integer startTime, Integer endTime){
        this.scheduleType = scheduleType;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setScheduleUtilList();
    }



    public void setScheduleUtilList(){
        List<ScheduleUtil> listScheduleUtil = new ArrayList<>();

        for(LocalDate date = this.startDate; date.isBefore(this.endDate); date = date.plusDays(1)){
            if (this.scheduleType == Constans.DAYS_OF_WEEK && !DayOfWeek.from(date).name().equals(this.dayOfTheWeek)){
                continue;
            }
            LinkedHashSet<String> hours = new LinkedHashSet<>();
            for(Integer i = this.startTime; i <= this.endTime; i++){
                hours.add(String.valueOf(i)+":00");
            }
            ScheduleUtil scheduleUtil = new ScheduleUtil(date, hours);
            listScheduleUtil.add(scheduleUtil);
        }
        this.scheduleUtilList = listScheduleUtil;
    }*/

}
