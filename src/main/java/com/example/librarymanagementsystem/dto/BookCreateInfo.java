package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BookCreateInfo {

    private String title;

    private String author;

    private String isbn;

    private int publicationYear;
}
