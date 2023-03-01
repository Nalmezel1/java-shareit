package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item create(Item item);

    Item update(Item item, long itemId);

    Optional<Item> get(long itemId);

    List<Item> search(String text);

    Optional<Item> delete(Long id);

    List<Item> getAll();
}
