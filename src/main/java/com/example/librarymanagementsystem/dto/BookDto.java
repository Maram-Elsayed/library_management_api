package com.example.librarymanagementsystem.dto;

import com.example.librarymanagementsystem.persistence.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private String isbn;

    private int publicationYear;

    private BookStatus status;
}
