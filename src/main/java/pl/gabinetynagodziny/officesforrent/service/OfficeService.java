package pl.gabinetynagodziny.officesforrent.service;

import org.springframework.transaction.annotation.Transactional;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.entity.Office;

import javax.persistence.Query;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

public interface OfficeService {

    Office mergeOffice(Office office);

    List<Office> findAll();

    List<Office> findByUserId(Long userId);

    List<Office> findSearch(Float priceMin, Float priceMax, Integer capacityMin, Long purposeId);

    Optional<Office> findByOfficeId(Long officeId);

    List<Office> findAllAccepted();

    List<Office> findAllNotAccepted();
}
