package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.exceptions.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final UserService userService;

    @Override
    public Item create(Item item, Long userId) throws ValidationException {
        if (userId == null) throw new ValidationException("Пустой ID");
        if (!userStorage.containsId(userId))
            throw new NotFoundException("акого пользователя нет");
        checkItem(item);

        item.setOwner(userService.get(userId));

        return itemStorage.create(item);
    }

    @Override
    public Item update(Item item, Long userId) throws ValidationException {
        if (userId == null) throw new ValidationException("Пустой ID");
        Item newItem = get(item.getId());
        if (!newItem.getOwner().getId().equals(userId))
            throw new NotFoundException("Не тот владелец");
        if (item.getName() != null) newItem.setName(item.getName());
        if (item.getDescription() != null) newItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) newItem.setAvailable(item.getAvailable());
        return itemStorage.update(newItem,newItem.getId());
    }

    @Override
    public Item get(Long id) {
        return itemStorage.get(id).orElseThrow(() -> {
            throw new NotFoundException("Такого предмета нет");
        });
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id).orElseThrow(() -> {
            throw new NotFoundException("Такого предмета нет");
        });
    }

    @Override
    public List<Item> getAll(Long userId) throws ValidationException {
        if (userId == null) throw new ValidationException("Такого пользователя нет.");
        return itemStorage.getAll()
                .stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override

        public List<Item> search(String text, Long userId) {
            if (text.isBlank()) return Collections.emptyList();
            return itemStorage.getAll()
                    .stream()
                    .filter(Item::getAvailable)
                    .filter(item -> item.getDescription() != null
                            && item.getDescription().toLowerCase().contains(text.toLowerCase())
                            || item.getName() != null
                            && item.getName().toLowerCase().contains(text.toLowerCase()))
                    .collect(Collectors.toList());
        }


    public void checkItem(Item item) throws ValidationException {
        if (item.getName() == null || item.getName().isBlank())
            throw new ValidationException("Не кооректное имя");
        if (item.getDescription() == null || item.getDescription().isBlank())
            throw new ValidationException("не корректный дескрипшен");
        if (item.getAvailable() == null)
            throw new ValidationException("не корректный Available");
    }
}
