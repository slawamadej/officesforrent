package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.Office;
import pl.gabinetynagodziny.officesforrent.repository.OfficeRepository;
import pl.gabinetynagodziny.officesforrent.service.OfficeService;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

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
}
