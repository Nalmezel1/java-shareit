package ru.practicum.shareit.item.storage;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Component
@RequiredArgsConstructor
public class ItemInMemoryStorage implements ItemStorage{
    private Long id = 0L;
    private Map<Long, Item> items = new HashMap<>();
    @Override
    public Item create(Item item) {
        item.setId(createId());
        items.put(item.getId(), item);
        return item;
    }
    @Override
    public Item update(Item item, long itemId) {
        items.put(itemId, item);
        return item;
    }
    @Override
    public Optional<Item> get(long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }
    @Override
    public Optional<Item> delete(Long id) {
        return Optional.ofNullable(items.remove(id));
    }
    @Override
    public List<Item> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<Item> search(String text) {
        return items.values().stream().filter(t -> t.getName().toLowerCase().contains(text.toLowerCase()) ||
                t.getDescription().toLowerCase().contains(text.toLowerCase()) && t.getAvailable()).collect(Collectors.toList());
    }

    public Long createId(){
        return ++id;
    }
}
