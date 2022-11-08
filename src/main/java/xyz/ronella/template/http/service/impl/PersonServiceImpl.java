package xyz.ronella.template.http.service.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import xyz.ronella.template.http.model.Person;
import xyz.ronella.template.http.repository.IPersonRepository;

import java.util.List;

public class PersonServiceImpl implements xyz.ronella.template.http.service.IPersonService {

    private final IPersonRepository repository;

    @Inject
    public PersonServiceImpl(@Named("Person") final IPersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(final Long id) {
        return repository.findById(id);
    }

    public Person create(final Person person) {
        return repository.create(person);
    }

    public Person update(final Person person) {
        return repository.update(person);
    }

    public void delete(final Long id) {
        repository.delete(id);
    }
}

