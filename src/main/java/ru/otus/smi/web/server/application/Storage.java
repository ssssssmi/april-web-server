package ru.otus.smi.web.server.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Storage {
    private static final Logger log = LogManager.getLogger(HttpServer.class.getName());
    private static List<Item> items;

    public static void init() {
        log.info("Storage initialized");
        items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Item("item " + i, 100 + (int)(Math.random() * 1000)));
        }
    }

    public static List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static Item getItemById(UUID id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public static void save(Item item) {
        item.setId(UUID.randomUUID());
        items.add(item);
    }

    public static void editInit(Item newItem) {
        for (Item item : items) {
            if (item.getId().equals(newItem.getId())) {
                item.setTitle(newItem.getTitle());
                item.setPrice(newItem.getPrice());
            }
        }
    }

    public static void delInit(UUID id) {
        items.removeIf(item -> item.getId().equals(id));
    }
}