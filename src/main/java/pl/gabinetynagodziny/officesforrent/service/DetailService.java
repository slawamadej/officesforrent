package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.Detail;

import java.util.List;
import java.util.Optional;

public interface DetailService {

    Detail mergeDetail(Detail detail);
    List<Detail> findAll();
    Optional<Detail> findByDetailId(Long detailId);
}
