package ru.practicum.shareit.item.service;

import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item, Long userId) throws ValidationException;

    Item update(Item item, Long id) throws ValidationException;

    Item get(Long id);

    void delete(Long id);

    List<Item> getAll(Long id) throws ValidationException;

    List<Item> search(String text, Long userId);
}
