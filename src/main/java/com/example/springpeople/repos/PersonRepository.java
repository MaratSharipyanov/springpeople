package com.example.springpeople.repos;

import com.example.springpeople.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findByLastnameContainingOrNameContaining (String lastname, String name);
}
