package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.exceptions.*;

import java.util.List;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User create(User user) throws ValidationException {
        if (getAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())))
            throw new EmailException("такой Email уже есть");
        return userStorage.create(user);
    }

    @Override
    public User update(User user) throws ValidationException {

        User newUser = get(user.getId());
        if (user.getName() != null) newUser.setName(user.getName());
        if (user.getEmail() != null) {
            if (getAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && !u.getId().equals(user.getId())))
                throw new EmailException("такой Email уже есть");
            newUser.setEmail(user.getEmail());
        }
        return userStorage.update(newUser, newUser.getId());
    }

    @Override
    public User get(Long id) throws ValidationException {
        if (id == null) throw new ValidationException("пустой ID");
        return userStorage.get(id).orElseThrow(() -> {
            throw new NotFoundException("не тот id");
        });
    }

    @Override
    public void delete(Long id) throws ValidationException {
        if (id == null) throw new ValidationException("пустой ID");
        userStorage.delete(id).orElseThrow(() -> {
            throw new NotFoundException("не тот id");
        });
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    private void userMailValidate(User user) {
        if (user.getEmail() == null)
            throw new EmailException("Email пустой");
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            throw new EmailException("Email не корректный");
        if (getAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())))
            throw new EmailException("такой Email уже есть");

    }
}
