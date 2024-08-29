package io.github.burymydeadhoreses;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@PersistenceUnit(unitName="person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String name;
    public int age;
}