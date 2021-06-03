package pl.gabinetynagodziny.officesforrent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.Unit;
import pl.gabinetynagodziny.officesforrent.repository.UnitRepository;

@Service
public class UnitServiceImpl implements UnitService{

    private final UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository){
        this.unitRepository = unitRepository;

    }

    @Override
    public Unit mergeUnit(Unit unit){
        unit.setUserId((long) 1);
        Unit mergedUnit = unitRepository.save(unit);
        return mergedUnit;
    }

}
