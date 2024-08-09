package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.BookCreateInfo;
import com.example.librarymanagementsystem.dto.BookDto;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.exception.ErrorMessage;
import com.example.librarymanagementsystem.persistence.entity.BookStatus;
import com.example.librarymanagementsystem.persistence.entity.Book;
import com.example.librarymanagementsystem.persistence.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService extends ValidationService {
    @Autowired
    BookRepository bookRepository;

    @Transactional
    @CacheEvict(value = "books", key = "'allBooks'")
    public BookDto createBook(BookCreateInfo bookCreateInfo) throws BaseValidationException {
        if(!bookRepository.findAllByIsbn(bookCreateInfo.getIsbn()).isEmpty()) {
            throw new BaseValidationException(ErrorMessage.ISBN_NOT_UNIQUE);
        }
        return createOrUpdateBook(bookCreateInfo, BookStatus.AVAILABLE, null);

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#id")
    public BookDto findBook(Long id) throws BaseValidationException {
        Book book = getBookById(id);
        return mapToBookDto(book);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "'allBooks'")
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToBookDto).collect(Collectors.toList());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "books", key = "'allBooks'"),
            @CacheEvict(value = "books", key = "#id")
    })
    public void deleteBook(Long id) throws BaseValidationException {
        Book book = getBookById(id);
        if(book.getStatus() == BookStatus.BORROWED) {
            throw new BaseValidationException(ErrorMessage.CAN_NOT_DELETE_BORROWED_BOOK);
        }
       bookRepository.delete(book);
    }

    @Transactional
    @CacheEvict(value = "books", key = "'allBooks'")
    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Long id, BookCreateInfo bookCreateInfo) throws BaseValidationException {
        Book book = getBookById(id);
        if(bookCreateInfo.getIsbn() != null && bookRepository.findByIsbnAndIdNot(bookCreateInfo.getIsbn(), book.getId()).isPresent()) {
            throw new BaseValidationException(ErrorMessage.ISBN_NOT_UNIQUE);
        }
        return createOrUpdateBook(bookCreateInfo, book.getStatus(), id);
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) throws BaseValidationException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BaseValidationException(ErrorMessage.ENTITY_NOT_FOUND, Book.class.getSimpleName()));
    }

    @Transactional
    public void updateStatus(Long bookId, BookStatus status) throws BaseValidationException {
        Book book = getBookById(bookId);
        book.setStatus(status);
        bookRepository.save(book);
    }

    @Transactional
    private BookDto createOrUpdateBook(BookCreateInfo bookCreateInfo, BookStatus status, Long id) throws BaseValidationException {
        Book book = Book.builder().id(id)
                .title(bookCreateInfo.getTitle())
                .author(bookCreateInfo.getAuthor())
                .publicationYear(bookCreateInfo.getPublicationYear())
                .isbn(bookCreateInfo.getIsbn())
                .status(status)
                .build();
        List<ConstraintViolation<?>> violations = validateNotNull(book);
        if (!violations.isEmpty()) {
            throw new BaseValidationException(ErrorMessage.VALIDATION_ERROR, violations);
        }
        book = bookRepository.save(book);

        return mapToBookDto(book);
    }

    public BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .status(book.getStatus())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .build();
    }

}
