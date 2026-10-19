package com.streaming.domain;

import java.time.LocalDateTime;

public final class Rating {

    private final User user;
    private final Content content;
    private final int value;
    private final LocalDateTime ratedAt;

    public Rating(User user, Content content, int value) {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне 1..10, а не " + value);
        }
        this.user = user;
        this.content = content;
        this.value = value;
        this.ratedAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public Content getContent() {
        return content;
    }

    public int getValue() {
        return value;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }
}
