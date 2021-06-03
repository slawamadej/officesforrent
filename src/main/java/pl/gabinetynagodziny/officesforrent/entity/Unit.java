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

    //  @OneToOne(cascade = CascadeType.MERGE)
    //  @JoinColumn(name = "userId")
    private Long userId;
    private Boolean consent;

    private String type; //person or company
    private String firstName;

    @NotNull(message="Please enter name")
    private String name;
    private String webpage;

    private String email;
    private String phoneNumber;

    private String taxNumber;

   // @OneToOne
   // @JoinColumn(name="userId", insertable = false, updatable = false)
   // private User user;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Office> offices;

   // public void addOffice(Office office){
   //     if(offices == null){
    //        offices = new ArrayList<>();
    //    }
//
  //      offices.add(office);
 //       office.setUnitId(this.unitId);
  //  }

    //   @OneToOne(cascade = CascadeType.MERGE) //sprawdz te kasksde o co cho
    //   @JoinColumn(name = "addressId")
    //   private Address address;

}

