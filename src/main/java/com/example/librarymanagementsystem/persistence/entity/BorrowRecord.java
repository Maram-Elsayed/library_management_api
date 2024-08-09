package com.example.librarymanagementsystem.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@Builder
@Entity
@Table(name = "borrowing_records")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "patron_id", referencedColumnName = "id")
    private Patron patron;

    @Basic
    @NotNull
    @Column(name = "borrow_date")
    private Long borrowDate;

    @Basic
    @Column(name = "return_date")
    private Long returnDate;
}
