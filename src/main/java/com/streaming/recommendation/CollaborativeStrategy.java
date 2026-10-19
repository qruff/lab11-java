package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.repository.RatingRepository;

/**
 * Коллаборативная стратегия (STRATEGY): рекомендует то, что высоко оценили
 * «похожие» пользователи. Скоринг делегируется {@link CollaborativeScorer}.
 */
public class CollaborativeStrategy extends AbstractRecommendationStrategy {

    public CollaborativeStrategy(RatingRepository ratingRepository) {
        super("Коллаборативная (по похожим пользователям)", ratingRepository);
    }

    @Override
    protected double score(Content content, RecommendationRequest request) {
        return CollaborativeScorer.score(request.getUser(), content, ratingRepository);
    }
}
