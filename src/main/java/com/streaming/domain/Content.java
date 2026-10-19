package com.streaming.domain;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Content implements CatalogItem {

    private final int id;
    private final String title;
    private final int releaseYear;
    private final String description;
    private final int durationMinutes;
    private final Set<Genre> genres = new LinkedHashSet<>();

    protected Content(int id, String title, int releaseYear, String description, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.durationMinutes = durationMinutes;
    }

    public abstract ContentType getType();

    public int getId() {
        return id;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Content addGenre(Genre genre) {
        genres.add(genre);
        return this;
    }

    public boolean hasGenre(Genre genre) {
        return genres.contains(genre);
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Duration getTotalDuration() {
        return Duration.ofMinutes(durationMinutes);
    }

    @Override
    public void display(String indent) {
        System.out.printf("%s- [%s] %s (%d), %d мин, жанры: %s%n",
                indent, getType().getTitle(), title, releaseYear, durationMinutes, genres);
    }

    @Override
    public String toString() {
        return "%s (%d)".formatted(title, releaseYear);
    }
}
