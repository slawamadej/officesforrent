package pl.gabinetynagodziny.officesforrent.entity;

import javax.persistence.*;

@Entity
@Table(name = "details")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="detailid")
    private Long detailId;

    @Column(name="detailtype")
    private String detailType; //ograniczenie na type np FURNISHINGS, PURPOSE
    private String detail;

    //i moze tutaj powinnm dodac obiekt office, tak zeby uzyskac to polaczenie jak w officedetail
    //private Office office
}
