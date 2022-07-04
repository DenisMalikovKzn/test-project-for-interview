package ru.project.service;

import ru.project.model.dto.animal.AddAnimalDto;
import ru.project.model.dto.animal.DeleteAnimalDto;
import ru.project.model.dto.animal.UpdateAnimalDto;
import ru.project.model.entity.Animal;

import java.util.List;

public interface AnimalService {

    Animal add(AddAnimalDto animal);

    Animal update(UpdateAnimalDto animal);

    Animal delete(DeleteAnimalDto animal);

    List<Animal> getAllAnimalsByUser(String username);

    Animal getInfoByAnimalId(Long id);

}
