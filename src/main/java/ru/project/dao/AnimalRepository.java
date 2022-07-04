package ru.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.entity.Animal;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {

    Optional<Animal> findByName(String name);

    List<Animal> findAllByOwner(String owner);
}
