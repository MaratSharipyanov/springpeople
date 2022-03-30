package com.example.springpeople.repos;

import com.example.springpeople.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
