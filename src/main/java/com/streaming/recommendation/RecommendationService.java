package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.repository.ContentRepository;

import java.util.List;

public class RecommendationService {

    private final ContentRepository contentRepository;
    private RecommendationStrategy strategy;

    public RecommendationService(ContentRepository contentRepository, RecommendationStrategy strategy) {
        this.contentRepository = contentRepository;
        this.strategy = strategy;
    }

    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public String currentStrategyName() {
        return strategy.name();
    }

    public List<Content> recommend(RecommendationRequest request) {
        return strategy.recommend(request, contentRepository.findAll());
    }
}
