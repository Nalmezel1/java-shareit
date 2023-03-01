package ru.practicum.shareit.user.storage;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EmailException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.User;

import java.util.*;

@Repository
@Component
@RequiredArgsConstructor
public class UserInMemoryStorage implements UserStorage{
    private Long id = 0L;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws ValidationException {


        userMailValidate(user);

        user.setId(createId());

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user,Long userId) {


        users.put(userId, user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return  Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> delete(Long id) {
        return  Optional.ofNullable(users.remove(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean containsId(Long id) {
        return users.containsKey(id);
    }

    private Long createId(){
        return ++id;
    }
    private void userMailValidate(User user) throws ValidationException {
        if (user.getEmail() == null)
            throw new ValidationException("Email пустой");
        if (user.getEmail().isBlank() || !user.getEmail().contains("@"))
            throw new ValidationException("Email не корректный");


    }

}
