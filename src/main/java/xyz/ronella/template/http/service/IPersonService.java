package xyz.ronella.template.http.service;

import xyz.ronella.template.http.model.Person;

import java.util.List;

public interface IPersonService {

    List<Person> findAll();

    Person findById(Long id);

    Person create(Person person);

    Person update(Person person);

    void delete(Long id);
}