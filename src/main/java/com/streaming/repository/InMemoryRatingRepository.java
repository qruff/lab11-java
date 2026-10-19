package com.streaming.repository;

import com.streaming.domain.Content;
import com.streaming.domain.Rating;
import com.streaming.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class InMemoryRatingRepository implements RatingRepository {

    private final List<Rating> ratings = new ArrayList<>();

    @Override
    public void save(Rating rating) {
        ratings.add(rating);
    }

    @Override
    public List<Rating> findByUser(User user) {
        List<Rating> result = new ArrayList<>();
        for (Rating r : ratings) {
            if (r.getUser().getId() == user.getId()) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public List<Rating> findByContent(Content content) {
        List<Rating> result = new ArrayList<>();
        for (Rating r : ratings) {
            if (r.getContent().getId() == content.getId()) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public OptionalDouble averageRating(Content content) {
        return ratings.stream()
                .filter(r -> r.getContent().getId() == content.getId())
                .mapToInt(Rating::getValue)
                .average();
    }

    @Override
    public long ratingCount(Content content) {
        return ratings.stream()
                .filter(r -> r.getContent().getId() == content.getId())
                .count();
    }
}
