package pl.gabinetynagodziny.officesforrent.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//moze nazwe zmienic, bo to ma byc dla oosby fizycznej lub dla firmy
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    private Boolean consent;

    private String type; //person or company
    private String firstName;

    @NotNull(message="Please enter name")
    private String name;
    private String webpage;

    private String email;
    private String phoneNumber;

    private String taxNumber;

    @OneToOne(mappedBy = "unit")
    private User user;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Office> offices;


}

