package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.repository.RatingRepository;

/**
 * Гибридная стратегия (STRATEGY) — взвешенная комбинация content-based,
 * коллаборативной и популярностной оценок
 */
public class HybridStrategy extends AbstractRecommendationStrategy {

    private final double genreWeight;
    private final double collaborativeWeight;
    private final double popularityWeight;

    public HybridStrategy(RatingRepository ratingRepository) {
        this(ratingRepository, 0.5, 0.3, 0.2);
    }

    public HybridStrategy(RatingRepository ratingRepository,
                          double genreWeight, double collaborativeWeight, double popularityWeight) {
        super("Гибридная", ratingRepository);
        this.genreWeight = genreWeight;
        this.collaborativeWeight = collaborativeWeight;
        this.popularityWeight = popularityWeight;
    }

    @Override
    protected double score(Content content, RecommendationRequest request) {
        double genreNorm = Math.min(genreOverlap(content, request), 3.0) / 3.0;     
        double collabNorm = CollaborativeScorer.score(request.getUser(), content, ratingRepository) / 10.0;
        double popNorm = ratingRepository.averageRating(content).orElse(0.0) / 10.0; 

        return genreWeight * genreNorm
                + collaborativeWeight * collabNorm
                + popularityWeight * popNorm;
    }
}
