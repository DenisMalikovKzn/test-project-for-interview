package ru.project.model.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Fish extends Animal {

    public Fish(String name, String sex, LocalDateTime birthday, String owner) {
        super(name, sex, birthday, owner);
    }

    public Fish() {
        super();
    }
}
