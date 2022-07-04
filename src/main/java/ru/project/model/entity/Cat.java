package ru.project.model.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Cat extends Animal {

    public Cat(String name, String sex, LocalDateTime birthday, String owner) {
        super(name, sex, birthday, owner);
    }

    public Cat() {
        super();
    }
}
