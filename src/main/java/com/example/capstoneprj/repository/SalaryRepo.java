package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.PayGrade;
import com.example.capstoneprj.domain.model.Salary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepo extends MongoRepository<Salary, String> {
    Optional<Salary> findByAmount(PayGrade amount);
}
