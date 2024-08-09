package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookCreateInfo;
import com.example.librarymanagementsystem.dto.BookDto;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> findAll() {
        return bookService.findAllBooks();
    }

    @GetMapping(value = "/{id}")
    public BookDto findBook(@PathVariable Long id) throws BaseValidationException{
        return bookService.findBook(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(@RequestBody BookCreateInfo bookCreateInfo) throws BaseValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookCreateInfo));
    }

    @PutMapping(value = "/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookCreateInfo bookCreateInfo) throws BaseValidationException {
        return bookService.updateBook(id, bookCreateInfo);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) throws BaseValidationException {
        bookService.deleteBook(id);
    }
}
