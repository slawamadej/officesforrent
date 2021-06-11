package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate reservationDate;
    private Integer reservationTime;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    private Boolean accepted = false;

    private LocalDateTime acceptanceDate;

    public void doAcceptance(){
        this.acceptanceDate = LocalDateTime.now();
        this.accepted = true;
    }

    public Reservation(Office office, User user, String reservationDateString, String reservationTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.office = office;
        this.user = user;
        this.reservationDate = LocalDate.parse(reservationDateString, formatter);
        this.reservationTime = Integer.parseInt(reservationTimeString.substring(0,reservationTimeString.indexOf(":")));
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedDate = LocalDateTime.now();
    }
}
