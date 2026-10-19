package com.streaming.recommendation;

import com.streaming.domain.Content;

import java.util.List;

public interface RecommendationStrategy {
    String name();

    List<Content> recommend(RecommendationRequest request, List<Content> catalog);
}
