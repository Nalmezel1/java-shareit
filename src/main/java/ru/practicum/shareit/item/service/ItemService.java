package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDtoRequest;
import ru.practicum.shareit.item.dto.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookingDate;

import java.util.List;

public interface ItemService {

    ItemDto create(ItemDto itemDto, long idOwner);

    ItemDto update(long itemId, ItemDto itemDto, long idOwner);

    ItemDtoWithBookingDate get(long itemId, long idOwner);

    List<ItemDtoWithBookingDate> getByOwner(long idOwner);

    List<ItemDto> search(String text);

    CommentDtoResponse createComment(CommentDtoRequest commentDto, long userId, long itemId);
}
