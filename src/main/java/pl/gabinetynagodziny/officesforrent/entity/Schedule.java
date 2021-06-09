package pl.gabinetynagodziny.officesforrent.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

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

/*
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="scheduleType", referencedColumnName="code", insertable = false, updatable = false)
    private DictionaryApp dictScheduleType;

    @ManyToOne(fetch=FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="dayOfTheWeek", referencedColumnName="code", insertable = false, updatable = false)
    private DictionaryApp dictDayOfTheWeek;
*/

}
