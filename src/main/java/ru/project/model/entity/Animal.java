package ru.project.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String sex;
    private LocalDateTime birthday;
    private String owner;

    public Animal() {
    }

    public Animal(String name, String sex, LocalDateTime birthday, String owner) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public static Animal buildAnimal(String name, String sex, LocalDateTime birthday, AnimalType type, String owner) {
        Animal animal;
        switch (type) {
            case BIRD:
                animal = new Bird(name, sex, birthday, owner);
                break;
            case CAT:
                animal = new Cat(name, sex, birthday, owner);
                break;
            case DOG:
                animal = new Dog(name, sex, birthday, owner);
                break;
            case FISH:
                animal = new Fish(name, sex, birthday, owner);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return animal;
    }
}
