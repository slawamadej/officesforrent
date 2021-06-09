package pl.gabinetynagodziny.officesforrent.service;

import pl.gabinetynagodziny.officesforrent.entity.DictionaryApp;

import java.util.List;

public interface DictionaryService {

    List<DictionaryApp> findAll();

    String findDescriptionByCode(String code);
}
