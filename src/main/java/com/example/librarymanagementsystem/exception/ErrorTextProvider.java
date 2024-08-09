package com.example.librarymanagementsystem.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ErrorTextProvider {
    public String getMessage(String messageKey, String... parameter) {
        return MessageFormat.format(ResourceBundle.getBundle("messages/errors")
                .getString(messageKey), (Object[]) parameter);
    }
}
