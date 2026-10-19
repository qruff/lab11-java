package com.streaming.domain;

public class Series extends Content {

    private final int seasons;
    private final int episodes;

    public Series(int id, String title, int releaseYear, String description,
                  int durationMinutes, int seasons, int episodes) {
        super(id, title, releaseYear, description, durationMinutes);
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public int getSeasons() {
        return seasons;
    }

    public int getEpisodes() {
        return episodes;
    }

    @Override
    public ContentType getType() {
        return ContentType.SERIES;
    }

    @Override
    public String toString() {
        return "%s (%d, %d сезон(ов))".formatted(getTitle(), getReleaseYear(), seasons);
    }
}
