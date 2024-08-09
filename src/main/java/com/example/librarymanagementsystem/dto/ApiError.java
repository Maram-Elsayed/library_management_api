package com.example.librarymanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ApiError {
    private String code;

    private String details;

    @Override
    public String toString() {
        return "Code: " + code + ", Details: " + details;
    }
}
