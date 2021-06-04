package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;

    private Long userId;

    @NotNull(message="Nie moze  puste")
    private String name;

    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer capacity;
    private Float area;

    private String photos;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || officeId == null) return null;

        return "/user-photos/" + officeId + "/" + photos;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="userId", insertable = false, updatable = false)
    private Unit unit;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "officeDetails", joinColumns = @JoinColumn(name = "officeId"), inverseJoinColumns = @JoinColumn(name = "detailId"))
    private List<Detail> details;

}
