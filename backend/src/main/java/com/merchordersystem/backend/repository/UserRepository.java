package com.merchordersystem.backend.repository;

import com.merchordersystem.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
