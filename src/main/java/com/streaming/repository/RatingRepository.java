package com.streaming.repository;

import com.streaming.domain.Content;
import com.streaming.domain.Rating;
import com.streaming.domain.User;

import java.util.List;
import java.util.OptionalDouble;


public interface RatingRepository {

    void save(Rating rating);

    List<Rating> findByUser(User user);

    List<Rating> findByContent(Content content);

    OptionalDouble averageRating(Content content);

    long ratingCount(Content content);
}
