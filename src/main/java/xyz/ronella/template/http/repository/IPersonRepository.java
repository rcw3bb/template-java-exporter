package xyz.ronella.template.http.repository;

import xyz.ronella.template.http.model.Person;

import java.util.List;

public interface IPersonRepository {
    List<Person> findAll();

    Person findById(Long id);

    Person create(Person person);

    Person update(Person person);

    void delete(Long id);
}
