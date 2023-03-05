package ru.practicum.shareit.exception;

public class EmailAlreadyExistException extends RuntimeException {
    private static final String EMAIL_EXIST = "Email занят";

    public EmailAlreadyExistException(String email) {
        super(String.format(EMAIL_EXIST, email));
    }
}