package ru.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.project.model.entity.Request;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    List<Request> findAllByIp(String ip);
}
