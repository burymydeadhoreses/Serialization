package io.github.burymydeadhoreses;

import jakarta.persistence.Persistence;

import java.io.*;

public class Main {
    public static final String path = "C:/Users/def/Desktop/result.bin";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var person = new Person();
        person.name = "Anderson";
        person.age = 17;

        serializePerson(person);

        Person deserializedPerson = (Person)deserializePerson();

        addPerson(deserializedPerson);

        System.out.println(deserializedPerson);

        addPerson(person);
    }

    public static void addPerson(Person person) {
        try (var entityManagerFactory = Persistence.createEntityManagerFactory("person")) {
            var entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            entityManager.persist(person);

            entityManager.getTransaction().commit();
        }
    }

    public static void updatePerson(long id, String name, int age) {
        try (var entityManagerFactory = Persistence.createEntityManagerFactory("person")) {
            var entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Person person = entityManager.find(Person.class, id);
            if (person == null)
                return;

            person.name = name;
            person.age = age;
            entityManager.merge(person);

            entityManager.getTransaction().commit();
        }
    }

    public static void deletePerson(long id) {
        try (var entityManagerFactory = Persistence.createEntityManagerFactory("person")) {
            var entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            var person = entityManager.find(Person.class, id);

            if (person != null)
                entityManager.remove(person);

            entityManager.getTransaction().commit();
        }

    }

    public static void serializePerson(Object obj) throws IOException {
        try(var fileOutStream = new FileOutputStream(path)) {
            var objectOutStream = new ObjectOutputStream(fileOutStream);

            objectOutStream.writeObject(obj);
        }
    }

    public static Object deserializePerson() throws IOException, ClassNotFoundException {
        try(var fileInputStream = new FileInputStream(path)) {
            var objectInputStream = new ObjectInputStream(fileInputStream);

            return objectInputStream.readObject();
        }
    }
}