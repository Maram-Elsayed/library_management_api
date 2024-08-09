package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookCreateInfo;
import com.example.librarymanagementsystem.dto.BookDto;
import com.example.librarymanagementsystem.persistence.entity.BookStatus;
import com.example.librarymanagementsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testFindAllBooks() throws Exception {
        List<BookDto> books = Arrays.asList(
                new BookDto(1L, "Title1", "Author1", "123456789", 2020, BookStatus.AVAILABLE),
                new BookDto(2L, "Title2", "Author2", "987654321", 2019, BookStatus.BORROWED)
        );

        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[1].title").value("Title2"));
    }

    @Test
    void testFindBookById() throws Exception {
        BookDto book = new BookDto(1L, "Title1", "Author1", "123456789", 2020, BookStatus.AVAILABLE);

        when(bookService.findBook(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.isbn").value("123456789"));
    }

    @Test
    void testCreateBook() throws Exception {
        BookDto book = new BookDto(1L, "Title1", "Author1", "123456789", 2020, BookStatus.AVAILABLE);

        when(bookService.createBook(any(BookCreateInfo.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Title1\",\"author\":\"Author1\",\"isbn\":\"123456789\",\"publicationYear\":2020}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title1"))
                .andExpect(jsonPath("$.isbn").value("123456789"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDto updatedBook = new BookDto(1L, "TitleUpdated", "AuthorUpdated", "123456789", 2021, BookStatus.AVAILABLE);

        when(bookService.updateBook(any(Long.class), any(BookCreateInfo.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"TitleUpdated\",\"author\":\"AuthorUpdated\",\"isbn\":\"123456789\",\"publicationYear\":2021}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TitleUpdated"))
                .andExpect(jsonPath("$.publicationYear").value(2021));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

}
