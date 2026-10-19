package com.streaming.domain;

public class Movie extends Content {

    public Movie(int id, String title, int releaseYear, String description, int durationMinutes) {
        super(id, title, releaseYear, description, durationMinutes);
    }

    @Override
    public ContentType getType() {
        return ContentType.MOVIE;
    }
}
