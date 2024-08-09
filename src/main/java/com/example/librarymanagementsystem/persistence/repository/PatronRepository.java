package com.example.librarymanagementsystem.persistence.repository;

import com.example.librarymanagementsystem.persistence.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

    List<Patron> findAllByPhone(String phone);

    @Query("SELECT p FROM Patron p WHERE p.phone = :phone AND p.id <> :id")
    Optional<Patron> findByPhoneAndIdNot(@Param("phone") String phone, @Param("id") Long id);
}
