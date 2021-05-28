package pl.gabinetynagodziny.officesforrent.entity;


import javax.persistence.*;

//moze nazwe zmienic, bo to ma byc dla oosby fizycznej lub dla firmy
@Entity
@Table(name = "entities")
public class Object {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="entityid")
    private Long entityId;

    //  @OneToOne(cascade = CascadeType.MERGE)
    //  @JoinColumn(name = "userId")
    @Column(name="userid")
    private Long userId;
    @Column(name="consentagree")
    private String consentAgree;

    private String type; //person or company
    @Column(name="firstname")
    private String firstName;
    private String name;
//    private String email;
//    private String phoneNumber;

    private String taxNumber;

    //   @OneToOne(cascade = CascadeType.MERGE) //sprawdz te kasksde o co cho
    //   @JoinColumn(name = "addressId")
    //   private Address address;

}

