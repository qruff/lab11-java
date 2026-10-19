package com.streaming.recommendation;

import com.streaming.domain.Content;
import com.streaming.domain.Rating;
import com.streaming.domain.User;
import com.streaming.repository.RatingRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Упрощённый user-based collaborative filtering.
 * «Соседями» считаются пользователи, которые высоко оценили хотя бы
 * один общий с целевым пользователем контент. Оценка контента = средний балл,
 * который поставили ему эти соседи
 */
final class CollaborativeScorer {

    private static final int LIKE_THRESHOLD = 7;

    private CollaborativeScorer() {
    }

    static double score(User target, Content content, RatingRepository ratings) {
        Set<Integer> targetLiked = likedContentIds(target, ratings);
        if (targetLiked.isEmpty()) {
            return 0.0;
        }

        List<Rating> contentRatings = ratings.findByContent(content);
        int sum = 0;
        int neighbours = 0;
        for (Rating r : contentRatings) {
            User other = r.getUser();
            if (other.getId() == target.getId()) {
                continue;
            }
            Set<Integer> otherLiked = likedContentIds(other, ratings);
            otherLiked.retainAll(targetLiked);
            if (!otherLiked.isEmpty()) {
                sum += r.getValue();
                neighbours++;
            }
        }
        return neighbours == 0 ? 0.0 : (double) sum / neighbours;
    }

    private static Set<Integer> likedContentIds(User user, RatingRepository ratings) {
        return ratings.findByUser(user).stream()
                .filter(r -> r.getValue() >= LIKE_THRESHOLD)
                .map(r -> r.getContent().getId())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
