package com.streaming.factory;

public class ContentData {

    public int id;
    public String title;
    public int releaseYear;
    public String description;
    public int durationMinutes;

    // для сериала
    public int seasons;
    public int episodes;

    // для документалок
    public String topic;

    public ContentData(int id, String title, int releaseYear, String description, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.durationMinutes = durationMinutes;
    }

    public ContentData withSeries(int seasons, int episodes) {
        this.seasons = seasons;
        this.episodes = episodes;
        return this;
    }

    public ContentData withTopic(String topic) {
        this.topic = topic;
        return this;
    }
}
