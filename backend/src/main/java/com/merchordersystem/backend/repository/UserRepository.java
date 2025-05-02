package com.merchordersystem.backend.repository;

import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:search IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<User> findByDynamicConditions(@Param("role") Role role,
                                       @Param("search") String search);




}
