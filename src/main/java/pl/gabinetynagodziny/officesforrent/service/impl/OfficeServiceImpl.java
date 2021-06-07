package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.repository.OfficeRepository;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @PersistenceContext
    protected EntityManager entityManager;

    public OfficeServiceImpl(OfficeRepository officeRepository){
        this.officeRepository = officeRepository;
    }

    @Override
    public Office mergeOffice(Office office){
        Office officeSaved = officeRepository.save(office);
        return officeSaved;
    }

    @Override
    public List<Office> findAll(){
        return officeRepository.findAll();
    }

    @Override
    public List<Office> findByUserId(Long userId) {
        return officeRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Office> findSearch(Float priceMin, Float priceMax, Integer capacityMin){
        return officeRepository.findSearch(priceMin, priceMax, capacityMin);
    }

    @Override
    public Optional<Office> findByOfficeId(Long officeId) {
        return officeRepository.findById(officeId);
    }


}
