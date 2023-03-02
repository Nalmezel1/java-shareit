package ru.practicum.shareit.user.storage;


import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user) throws ValidationException;

    User update(User user, Long userId);

    Optional<User> get(Long id);

    Optional<User> delete(Long id);

    List<User> getAll();

    boolean containsId(Long id);
}
