package com.example.repository;


import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("update User u set u.email = :email where u.id = :id")
    void updateEmail(@Param("id") Long id, @Param("email") String email);

    @Modifying
    @Query("update User u set u.name = :name where u.id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);
}
