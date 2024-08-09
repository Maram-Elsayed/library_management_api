package com.example.librarymanagementsystem.persistence.repository;

import com.example.librarymanagementsystem.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn AND b.id <> :id")
    Optional<Book> findByIsbnAndIdNot(@Param("isbn") String isbn, @Param("id") Long id);
}
