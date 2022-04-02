package com.example.springpeople.repos;

import com.example.springpeople.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByLastnameContainingOrNameContaining (String lastname, String name);
}
