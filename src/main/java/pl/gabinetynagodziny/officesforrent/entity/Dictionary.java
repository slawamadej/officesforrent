package pl.gabinetynagodziny.officesforrent.entity;

import javax.persistence.*;

@Entity
@Table(name = "dictionary")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dictid")
    private Long dictId;

    @Column(name="dicttype")
    private String dictType; //TYPE or DETAIL ??? nie musi byc, ale w sumie by sie przydalo
    private String language;
    private String code;
    private String description;

}
