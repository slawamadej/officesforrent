package pl.gabinetynagodziny.officesforrent.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto {

    private Long unitId;

    private Long userId;
    private Boolean consent;

    private String type;
    private String firstName;
    private String name;
    private String webpage;

    private String email;
    private String phoneNumber;

    private String taxNumber;
}
