package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserService {
    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

    User get(Long id) throws ValidationException;

    void delete(Long id) throws ValidationException;

    List<User> getAll();
}
