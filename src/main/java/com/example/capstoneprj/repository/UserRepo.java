package com.example.capstoneprj.repository;

import com.example.capstoneprj.domain.model.PayGrade;
import com.example.capstoneprj.domain.model.UserModel;
import com.mongodb.lang.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByPayGrade(PayGrade payGrade);
    Optional<List<UserModel>> findByEmailLikeOrLastNameContainingOrFirstNameContaining(@Nullable String email,@Nullable String lastName, String firstName);
    Optional<List<UserModel>> findByEmailLikeAndLastNameContaining(@Nullable String email,@Nullable String lastName);
//    @Query("SELECT u FROM _User u WHERE u.email LIKE CONCAT('%',:email,'%') OR u.username LIKE CONCAT('%',:username,'%')")
//    Optional<List<UserModel>> findLikeEmailOrLikeUsername (@Param("email") String email, @Param("username") String username);
}
