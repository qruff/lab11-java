package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.repository.RatingRepository;

/**
 * Стратегия по популярности (STRATEGY): оценка = средний рейтинг с небольшой
 * поправкой на число оценок (чем больше оценок, тем надёжнее рейтинг).
 */
public class PopularityStrategy extends AbstractRecommendationStrategy {

    public PopularityStrategy(RatingRepository ratingRepository) {
        super("По популярности", ratingRepository);
    }

    @Override
    protected double score(Content content, RecommendationRequest request) {
        double avg = ratingRepository.averageRating(content).orElse(0.0);
        long count = ratingRepository.ratingCount(content);
        return avg + count * 0.01;
    }
}
