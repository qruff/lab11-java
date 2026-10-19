package com.streaming.domain;

public class Documentary extends Content {

    private final String topic;

    public Documentary(int id, String title, int releaseYear, String description,
                       int durationMinutes, String topic) {
        super(id, title, releaseYear, description, durationMinutes);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public ContentType getType() {
        return ContentType.DOCUMENTARY;
    }
}
