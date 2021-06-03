package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.Office;

import java.util.List;

public interface OfficeService {

    Office mergeOffice(Office office);

    List<Office> findAll();
}
