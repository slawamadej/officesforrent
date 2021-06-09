package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gabinetynagodziny.officesforrent.util.Constans;
import pl.gabinetynagodziny.officesforrent.util.ScheduleUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;

    private Long userId;

    private String name;
    private String description;

    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer capacity;
    private Float area;

    private String photos;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="userId", insertable = false, updatable = false)
    private Unit unit;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "officeDetails", joinColumns = @JoinColumn(name = "officeId"), inverseJoinColumns = @JoinColumn(name = "detailId"))
    private List<Detail> details;

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy = "office",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Schedule> schedules;

    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
        schedule.setOffice(this);
    }

    public void removeSchedule(Schedule schedule){
        schedules.remove(schedule);
        schedule.setOffice(null);
    }

    @Transient
    private SortedMap<LocalDate, LinkedHashSet<String>> officeScheduleMap;

    public void refreshOfficeScheduleMap(){
       //ustwic this.schedules w kolejnosci od working_day, day_of_the_week, date
        //bo potem bede nadpisywac dla danej daty
            List<Schedule> officeScheduleList = this.schedules;
            List<ScheduleUtil> scheduleUtilList = new ArrayList<>();
            SortedMap<LocalDate, LinkedHashSet<String>> officeScheduleMap = new TreeMap<>();

        for (int i = 0; i < this.schedules.size(); i++){
            Schedule schedule = this.schedules.get(i);
            //for the rule WORKING_DAYS doesn't create schedule unit for saturday and sunday
            //for the rule DAYS_OF_WEEK create schedule unit only for dselected ayoftheweek
            for(LocalDate date = schedule.getStartDate(); date.isBefore(schedule.getEndDate()); date = date.plusDays(1)){
                System.out.println(date);
                System.out.println(DayOfWeek.from(date).name());
                System.out.println(schedule.getDayOfTheWeek());
                if (Constans.DAYS_OF_WEEK.equals(schedule.getScheduleType())  && !DayOfWeek.from(date).name().equals(schedule.getDayOfTheWeek())
                || Constans.WORKING_DAYS.equals(schedule.getScheduleType()) && (DayOfWeek.from(date).name().equals(Constans.SATURDAY) || DayOfWeek.from(date).name().equals(Constans.SUNDAY))){
                    continue;
                }
                LinkedHashSet<String> hours = new LinkedHashSet<>();
                for(int j = schedule.getStartTime(); j < schedule.getEndTime(); j++){
                    hours.add(String.valueOf(j)+":00");
                }
                //for date hours list is override, schedules list is sorted from the least important rule
                officeScheduleMap.put(date, hours);
            }
        }

        this.setOfficeScheduleMap(officeScheduleMap);

    }

}
