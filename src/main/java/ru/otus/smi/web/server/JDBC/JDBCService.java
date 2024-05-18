package ru.otus.smi.web.server.JDBC;

import ru.otus.smi.web.server.application.Item;

import java.util.List;
import java.util.UUID;

public interface JDBCService {
    List<Item> getItems();

    Item getItemById(UUID id);

    void addItems(Item item);

    void editItem(Item newItem);

    void delInit(UUID id);
}
