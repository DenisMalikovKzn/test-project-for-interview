package ru.fratask.service;

import org.springframework.stereotype.Service;
import ru.fratask.dao.AnimalRepository;
import ru.fratask.model.AppException;
import ru.fratask.model.AppResponseCode;
import ru.fratask.model.dto.animal.AddAnimalDto;
import ru.fratask.model.dto.animal.DeleteAnimalDto;
import ru.fratask.model.dto.animal.UpdateAnimalDto;
import ru.fratask.model.entity.Animal;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal add(AddAnimalDto animalDto) {
        if (animalRepository.findByName(animalDto.getName()).isPresent()) {
            throw new AppException(AppResponseCode.ANIMAL_ALREADY_EXISTS);
        }
        Animal animal = Animal.buildAnimal(animalDto.getName(), animalDto.getSex(), animalDto.getBirthday(), animalDto.getType(), animalDto.getOwner());
        return animalRepository.save(animal);
    }

    public Animal update(UpdateAnimalDto animalDto) {
        Animal animal = animalRepository.findByName(animalDto.getName()).orElseThrow(() -> new AppException(AppResponseCode.ANIMAL_DOES_NOT_EXISTS));
        if (!animal.getOwner().equals(animalDto.getOwner())) {
            throw new AppException(AppResponseCode.ANIMAL_HAS_ANOTHER_OWNER_PERMISSION_DENIED);
        }
        animal.setSex(animal.getSex());
        animal.setBirthday(animalDto.getBirthday());
        animalRepository.save(animal);
        return animal;
    }

    public Animal delete(DeleteAnimalDto animalDto) {
        Animal animal = animalRepository.findByName(animalDto.getName()).orElseThrow(() -> new AppException(AppResponseCode.ANIMAL_DOES_NOT_EXISTS));
        if (!animal.getOwner().equals(animalDto.getOwner())) {
            throw new AppException(AppResponseCode.ANIMAL_HAS_ANOTHER_OWNER_PERMISSION_DENIED);
        }
        animalRepository.delete(animal);
        return animal;
    }

    public List<Animal> getAllAnimalsByUser(String username) {
        return animalRepository.findAllByOwner(username);
    }

    public Animal getInfoByAnimalId(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new AppException(AppResponseCode.ANIMAL_DOES_NOT_EXISTS));
    }
}
