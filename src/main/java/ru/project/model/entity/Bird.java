package ru.project.model.entity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Bird extends Animal {

    public Bird(String name, String sex, LocalDateTime birthday, String owner) {
        super(name, sex, birthday, owner);
    }

    public Bird() {
        super();
    }
}
