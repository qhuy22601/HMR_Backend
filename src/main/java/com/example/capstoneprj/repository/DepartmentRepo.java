package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends MongoRepository<Department, String> {
    Optional<Department> findByDepartName(String name);
}
