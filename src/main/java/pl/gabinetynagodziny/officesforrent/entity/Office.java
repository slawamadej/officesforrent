package pl.gabinetynagodziny.officesforrent.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="officeid")
    private Long officeId;

    @Column(name="entityid")
    private Long entityId;

    private String name;
    private Integer capacity;
    private Float area;

    //ogarnac kiedy jest joincolumn name = officeId
    //a co bedzie jak bedzie mappedby mappedby jest przy relacji

    //@OneToMany(mappedBy="detail")
    //private List<Detail> officeDetails =
    //        new ArrayList<>();

    //zastanawiam sie tutaj w sumie moge zrobic liste office details?????
    // i wtedy bez office details entity

}
