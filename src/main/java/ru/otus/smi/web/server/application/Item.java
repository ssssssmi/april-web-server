package ru.otus.smi.web.server.application;

import java.util.UUID;

public class Item {
    private UUID id;
    private String title;
    private int price;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public Item(UUID id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}
