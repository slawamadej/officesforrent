package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedDate = LocalDateTime.now();
    }
}
