package com.example.librarymanagementsystem.persistence.entity;

import com.example.librarymanagementsystem.constant.Constant;
import com.example.librarymanagementsystem.exception.ErrorMessage;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Builder
@Entity
@Table(name = "books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = ErrorMessage.TITLE_NOT_EMPTY)
    @NotNull(message = ErrorMessage.TITLE_NOT_EMPTY)
    @Size(max = Constant.NAME_MAX_SIZE, message = ErrorMessage.NAME_MAX_LENGTH_EXCEEDED)
    @Column
    private String title;


    @NotBlank(message = ErrorMessage.AUTHOR_NOT_EMPTY)
    @NotNull(message = ErrorMessage.AUTHOR_NOT_EMPTY)
    @Size(max = Constant.NAME_MAX_SIZE, message = ErrorMessage.AUTHOR_MAX_LENGTH_EXCEEDED)
    @Column
    private String author;

    @NotBlank(message = ErrorMessage.ISBN_NOT_EMPTY)
    @NotNull(message = ErrorMessage.ISBN_NOT_EMPTY)
    @Size(max = Constant.ISBN_MAX_LENGTH, min = Constant.ISBN_MIN_LENGTH, message = ErrorMessage.INVALID_ISBN_LENGTH)
    @Column
    private String isbn;

    @Basic
    @Column(name = "publication_year")
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private BookStatus status = BookStatus.AVAILABLE;

}
