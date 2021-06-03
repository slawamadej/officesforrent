package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offices")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long officeId;

    private Long unitId;

    private String name;
    private Integer capacity;
    private Float area;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="unitId", insertable = false, updatable = false)
    private Unit unit;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "officeDetails", joinColumns = @JoinColumn(name = "officeId"), inverseJoinColumns = @JoinColumn(name = "detailId"))
    private List<Detail> details;


}
