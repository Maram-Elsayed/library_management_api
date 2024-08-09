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
@Table(name = "patrons")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = ErrorMessage.NAME_NOT_EMPTY)
    @NotNull(message = ErrorMessage.NAME_NOT_EMPTY)
    @Size(max = Constant.NAME_MAX_SIZE, message = ErrorMessage.NAME_MAX_LENGTH_EXCEEDED)
    @Column
    private String name;

    @NotBlank(message = ErrorMessage.PHONE_NOT_EMPTY)
    @NotNull(message = ErrorMessage.PHONE_NOT_EMPTY)
    @Column
    private String phone;

}
