package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    private String detailType; //TYPE or DETAIL ??? nie musi byc, ale w sumie by sie przydalo//ograniczenie na type np FURNISHINGS, PURPOSE
    //private String language;
    private String code;
    private String description;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "officeDetails", joinColumns = @JoinColumn(name = "detailId"), inverseJoinColumns = @JoinColumn(name = "officeId"))
    private List<Office> offices;


    //i moze tutaj powinnm dodac obiekt office, tak zeby uzyskac to polaczenie jak w officedetail
    //private Office office
}
