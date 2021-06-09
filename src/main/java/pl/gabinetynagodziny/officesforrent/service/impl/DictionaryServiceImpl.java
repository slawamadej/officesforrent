package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;
import pl.gabinetynagodziny.officesforrent.repository.DictionaryRepository;
import pl.gabinetynagodziny.officesforrent.service.DictionaryService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository){
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<DictionaryApp> findAll() {
        return  dictionaryRepository.findAll();
    }

    @Override
    public String findDescriptionByCode(String code) {
        return dictionaryRepository.findDescriptionByCode(code);
    }
}
