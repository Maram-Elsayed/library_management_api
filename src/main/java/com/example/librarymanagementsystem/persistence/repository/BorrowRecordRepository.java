package com.example.librarymanagementsystem.persistence.repository;

import com.example.librarymanagementsystem.persistence.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findAllByPatronIdAndReturnDateIsNull(Long patronId);

    Optional<BorrowRecord> findOneByBookIdAndPatronId(Long bookId, Long patronId);
}
