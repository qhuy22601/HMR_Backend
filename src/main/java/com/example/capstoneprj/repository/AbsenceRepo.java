package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.Absence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepo extends MongoRepository<Absence, String> {
    @Query(value = "{'eventDate':{ $gte: ?0, $lte: ?1}}")
    Optional<Absence> findByAbsenceDateBetween(LocalDate start, LocalDate end);

    List<Absence> findByUnreadTrue();

    List<Absence> findByUserId(String id);

}
