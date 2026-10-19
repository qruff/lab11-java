package com.streaming.domain;

public enum ContentType {
    MOVIE("Фильм"),
    SERIES("Сериал"),
    DOCUMENTARY("Документальный");

    private final String title;

    ContentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
