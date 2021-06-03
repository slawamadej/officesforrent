package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.Detail;
import pl.gabinetynagodziny.officesforrent.repository.DetailRepository;
import pl.gabinetynagodziny.officesforrent.service.DetailService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailServiceImpl implements DetailService {

    private final DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository){ this.detailRepository = detailRepository; }

    @Override
    public Detail mergeDetail(Detail detail) {
        Detail detailSaved = detailRepository.save(detail);
        //chyba do listy trzeba to jeszcze dodac dla obiektu
        return detailSaved;
    }

    @Override
    public List<Detail> findAll() {
        return detailRepository.findAll();
    }

    @Override
    public Optional<Detail> findByDetailId(Long detailId) {
        return detailRepository.findByDetailId(detailId);
    }


}
