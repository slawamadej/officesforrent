package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;
import pl.gabinetynagodziny.officesforrent.repository.DictionaryAppRepository;
import pl.gabinetynagodziny.officesforrent.service.DictionaryAppService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DictionaryAppServiceImpl implements DictionaryAppService {

    private final DictionaryAppRepository dictionaryAppRepository;

    public DictionaryAppServiceImpl(DictionaryAppRepository dictionaryAppRepository){
        this.dictionaryAppRepository = dictionaryAppRepository;
    }

    @Override
    public List<DictionaryApp> findAll() {
        return  dictionaryAppRepository.findAll();
    }
}
