package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.repository.RatingRepository;

/**
 * Content-based стратегия (STRATEGY): чем больше жанров контента совпадает
 * с любимыми жанрами пользователя, тем выше оценка. Переопределяет только
 * шаг скоринга шаблонного метода.
 */
public class GenreBasedStrategy extends AbstractRecommendationStrategy {

    public GenreBasedStrategy(RatingRepository ratingRepository) {
        super("По жанрам (content-based)", ratingRepository);
    }

    @Override
    protected double score(Content content, RecommendationRequest request) {
        double overlap = genreOverlap(content, request);
        double avg = ratingRepository.averageRating(content).orElse(0.0);
        return overlap + avg * 0.001;
    }
}
