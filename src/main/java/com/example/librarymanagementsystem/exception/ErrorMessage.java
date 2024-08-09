package com.example.librarymanagementsystem.exception;

public class ErrorMessage {
    public static final String TITLE_NOT_EMPTY = "TITLE_NOT_EMPTY";

    public static final String NAME_NOT_EMPTY = "NAME_NOT_EMPTY";

    public static final String ISBN_NOT_EMPTY = "ISBN_NOT_EMPTY";

    public static final String AUTHOR_NOT_EMPTY = "AUTHOR_NOT_EMPTY";

    public static final String NAME_MAX_LENGTH_EXCEEDED = "NAME_MAX_LENGTH_EXCEEDED";

    public static final String AUTHOR_MAX_LENGTH_EXCEEDED = "AUTHOR_MAX_LENGTH_EXCEEDED";

    public static final String INVALID_ISBN_LENGTH = "INVALID_ISBN_LENGTH";

    public static final String PHONE_NOT_EMPTY = "PHONE_NOT_EMPTY";

    public static final String ENTITY_NOT_FOUND = "ENTITY_NOT_FOUND";

    public static final String CAN_NOT_DELETE_BORROWED_BOOK = "CAN_NOT_DELETE_BORROWED_BOOK";

    public static final String ISBN_NOT_UNIQUE = "ISBN_NOT_UNIQUE";

    public static final String PHONE_NOT_UNIQUE = "PHONE_NOT_UNIQUE";

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    public static final String CAN_NOT_DELETE_PATRON_WITH_BORROWED_BOOK = "CAN_NOT_DELETE_PATRON_WITH_BORROWED_BOOK";

    public static final String BORROW_DATE_REQUIRED = "BORROW_DATE_REQUIRED";

    public static final String BOOK_BORROWED = "BOOK_BORROWED";

    public static final String RETURN_DATE_REQUIRED = "RETURN_DATE_REQUIRED";

    public static final String RETURN_DATE_BEFORE_BORROW_DATE = "RETURN_DATE_BEFORE_BORROW_DATE";
}
