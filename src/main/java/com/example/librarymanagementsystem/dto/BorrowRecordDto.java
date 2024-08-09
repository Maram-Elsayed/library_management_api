package com.example.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
public class BorrowRecordDto {
    private Long id;

    private BookDto book;

    private PatronDto patron;

    private Long borrowDate;

    private Long returnDate;
}
