package xyz.ronella.template.http.repository.impl;

import xyz.ronella.template.http.model.Person;
import xyz.ronella.template.http.repository.IPersonRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PersonListRepository implements IPersonRepository {

    private static final List<Person> persons = new ArrayList<>();

    static {
        final var person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("Ronaldo");
        person1.setLastName("Webb");

        final var person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Juan");
        person2.setLastName("Dela Cruz");

        persons.add(person1);
        persons.add(person2);
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Person findById(final Long id) {
        return persons.stream().filter(___person -> id.equals(___person.getId())).findFirst().orElse(null);
    }

    @Override
    public Person create(final Person person) {
        person.setId(persons.stream().max(Comparator.comparingLong(Person::getId)).orElseGet(()-> {
            final var defaultPerson = new Person();
            defaultPerson.setId(-1L);
            return defaultPerson;
        }).getId() + 1);
        persons.add(person);
        return person;
    }

    @Override
    public Person update(final Person person) {
        final var updatedPerson = findById(person.getId());
        updatedPerson.setFirstName(person.getFirstName());
        updatedPerson.setLastName(person.getLastName());
        return updatedPerson;
    }

    @Override
    public void delete(final Long id) {
        final var deletePerson = findById(id);
        persons.remove(deletePerson);
    }
}

