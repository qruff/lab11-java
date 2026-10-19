package com.streaming.recommendation;

import com.streaming.domain.Genre;
import com.streaming.domain.User;

import java.util.LinkedHashSet;
import java.util.Set;

public final class RecommendationRequest {

    private final User user;
    private final Set<Genre> genres;
    private final int minYear;
    private final double minRating;
    private final int limit;

    private RecommendationRequest(Builder b) {
        this.user = b.user;
        this.genres = b.genres;
        this.minYear = b.minYear;
        this.minRating = b.minRating;
        this.limit = b.limit;
    }

    public static Builder builder(User user) {
        return new Builder(user);
    }

    public User getUser() {
        return user;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public int getMinYear() {
        return minYear;
    }

    public double getMinRating() {
        return minRating;
    }

    public int getLimit() {
        return limit;
    }

    public static final class Builder {
        private final User user;
        private final Set<Genre> genres = new LinkedHashSet<>();
        private int minYear = 0;
        private double minRating = 0.0;
        private int limit = 10;

        private Builder(User user) {
            this.user = user;
        }

        public Builder genres(Genre... genres) {
            for (Genre g : genres) {
                this.genres.add(g);
            }
            return this;
        }

        public Builder minYear(int minYear) {
            this.minYear = minYear;
            return this;
        }

        public Builder minRating(double minRating) {
            this.minRating = minRating;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public RecommendationRequest build() {
            return new RecommendationRequest(this);
        }
    }
}
