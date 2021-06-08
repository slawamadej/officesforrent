package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Builder
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

   // @DateTimeFormat(pattern = "HH:mm")
    private java.sql.Time startTime;
   // @DateTimeFormat(pattern = "HH:mm")
    private java.sql.Time endTime;

    private String dayOfTheWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

}
