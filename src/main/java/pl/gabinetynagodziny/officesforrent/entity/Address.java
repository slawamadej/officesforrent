package pl.gabinetynagodziny.officesforrent.entity;


import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="addressid")
    private Long addressId;

    private String town;
    private String district;
    private String street;
    private String postcode;
    private String flatNo;
    private Float latitude;
    private Float longitude;

    public Address(){

    }
}