package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BorrowBookInfo;
import com.example.librarymanagementsystem.dto.BorrowRecordDto;
import com.example.librarymanagementsystem.dto.ReturnBookInfo;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping(value = "borrow/{bookId}/patron/{patronId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowRecordDto borrowBook(@PathVariable Long bookId, @PathVariable Long patronId, @RequestBody BorrowBookInfo borrowBookInfo) throws BaseValidationException {
        return borrowRecordService.borrowBook(bookId, patronId, borrowBookInfo);
    }

    @PutMapping(value = "return/{bookId}/patron/{patronId}")
    public BorrowRecordDto returnBook(@PathVariable Long bookId, @PathVariable Long patronId, @RequestBody ReturnBookInfo returnBookInfo) throws BaseValidationException {
        return borrowRecordService.returnBook(bookId, patronId, returnBookInfo);
    }
}
