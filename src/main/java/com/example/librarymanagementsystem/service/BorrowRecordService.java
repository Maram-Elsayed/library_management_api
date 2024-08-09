package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.BorrowBookInfo;
import com.example.librarymanagementsystem.dto.BorrowRecordDto;
import com.example.librarymanagementsystem.dto.ReturnBookInfo;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.exception.ErrorMessage;
import com.example.librarymanagementsystem.persistence.entity.Book;
import com.example.librarymanagementsystem.persistence.entity.BookStatus;
import com.example.librarymanagementsystem.persistence.entity.BorrowRecord;
import com.example.librarymanagementsystem.persistence.entity.Patron;
import com.example.librarymanagementsystem.persistence.repository.BorrowRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BorrowRecordService {
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private PatronService patronService;

    @Transactional
    public BorrowRecordDto borrowBook(Long bookId, Long patronId, BorrowBookInfo borrowBookInfo) throws BaseValidationException {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        if(book.getStatus() == BookStatus.BORROWED) {
            throw new BaseValidationException(ErrorMessage.BOOK_BORROWED);
        }
        if(borrowBookInfo.getBorrowDate() == null) {
            throw new BaseValidationException(ErrorMessage.BORROW_DATE_REQUIRED);
        }
        BorrowRecord borrowRecord = BorrowRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(borrowBookInfo.getBorrowDate())
                .build();
        borrowRecord = borrowRecordRepository.save(borrowRecord);
        bookService.updateStatus(book.getId(), BookStatus.BORROWED);
        return mapToBorrowRecordDto(borrowRecord);
    }

    @Transactional
    public BorrowRecordDto returnBook(Long bookId, Long patronId, ReturnBookInfo returnBookInfo) throws BaseValidationException {
        Book book = bookService.getBookById(bookId);
        patronService.getPatronById(patronId);
        BorrowRecord borrowRecord = getRecordByBookAndPatron(bookId, patronId);
        Long returnDate = returnBookInfo.getReturnDate();

        if(returnDate == null) {
            throw new BaseValidationException(ErrorMessage.RETURN_DATE_REQUIRED);
        }
        if(returnDate < borrowRecord.getBorrowDate()) {
            throw new BaseValidationException(ErrorMessage.RETURN_DATE_BEFORE_BORROW_DATE);
        }

        borrowRecord.setReturnDate(returnDate);
        borrowRecord = borrowRecordRepository.save(borrowRecord);
        bookService.updateStatus(book.getId(), BookStatus.AVAILABLE);
        return mapToBorrowRecordDto(borrowRecord);
    }

    private BorrowRecordDto mapToBorrowRecordDto (BorrowRecord borrowRecord) {
        return BorrowRecordDto.builder()
                .id(borrowRecord.getId())
                .book(bookService.mapToBookDto(borrowRecord.getBook()))
                .patron(patronService.mapToPatronDto(borrowRecord.getPatron()))
                .borrowDate(borrowRecord.getBorrowDate())
                .returnDate(borrowRecord.getReturnDate())
                .build();
    }

    private BorrowRecord getRecordByBookAndPatron(Long bookId, Long patronId) throws BaseValidationException {
        return borrowRecordRepository.findOneByBookIdAndPatronId(bookId, patronId)
                .orElseThrow(() -> new BaseValidationException(ErrorMessage.ENTITY_NOT_FOUND, BorrowRecord.class.getSimpleName()));
    }

}
