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
@Table(name = "dictionaries")
public class DictionaryApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dictId;

    private String dictCode;
    private String code;
    private String description;

 /*   @OneToMany(mappedBy = "dictScheduleType")
    private List<Schedule> schedulesTypes;
*/
}
