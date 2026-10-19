package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.domain.Genre;
import com.streaming.repository.RatingRepository;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractRecommendationStrategy implements RecommendationStrategy {

    private final String name;
    protected final RatingRepository ratingRepository;

    protected AbstractRecommendationStrategy(String name, RatingRepository ratingRepository) {
        this.name = name;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final List<Content> recommend(RecommendationRequest request, List<Content> catalog) {
        return selectCandidates(catalog).stream()
                .filter(c -> passesFilters(c, request))
                .sorted(Comparator.comparingDouble((Content c) -> score(c, request)).reversed())
                .limit(request.getLimit())
                .toList();
    }

    protected List<Content> selectCandidates(List<Content> catalog) {
        return catalog;
    }

    protected boolean passesFilters(Content content, RecommendationRequest request) {
        if (alreadyRatedByUser(content, request)) {
            return false;
        }
        if (content.getReleaseYear() < request.getMinYear()) {
            return false;
        }
        if (!request.getGenres().isEmpty()
                && request.getGenres().stream().noneMatch(content::hasGenre)) {
            return false;
        }
        if (request.getMinRating() > 0) {
            double avg = ratingRepository.averageRating(content).orElse(0.0);
            return avg >= request.getMinRating();
        }
        return true;
    }

    protected abstract double score(Content content, RecommendationRequest request);

    protected boolean alreadyRatedByUser(Content content, RecommendationRequest request) {
        return ratingRepository.findByUser(request.getUser()).stream()
                .anyMatch(r -> r.getContent().getId() == content.getId());
    }

    protected long genreOverlap(Content content, RecommendationRequest request) {
        return content.getGenres().stream()
                .filter(g -> isPreferred(g, request))
                .count();
    }

    protected boolean isPreferred(Genre genre, RecommendationRequest request) {
        return request.getUser().getPreferredGenres().contains(genre)
                || request.getGenres().contains(genre);
    }
}
