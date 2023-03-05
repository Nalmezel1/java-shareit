package ru.practicum.shareit.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity) {
        super(String.format("error", entity));
    }
}