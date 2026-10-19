package com.streaming.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements CatalogItem {

    private final String title;
    private final List<CatalogItem> items = new ArrayList<>();

    public Playlist(String title) {
        this.title = title;
    }

    public Playlist add(CatalogItem item) {
        items.add(item);
        return this;
    }

    public void remove(CatalogItem item) {
        items.remove(item);
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Duration getTotalDuration() {
        Duration total = Duration.ZERO;
        for (CatalogItem item : items) {
            total = total.plus(item.getTotalDuration());
        }
        return total;
    }

    @Override
    public void display(String indent) {
        System.out.printf("%s* Плейлист \"%s\" (итого %d мин):%n",
                indent, title, getTotalDuration().toMinutes());
        for (CatalogItem item : items) {
            item.display(indent + "    ");
        }
    }
}
