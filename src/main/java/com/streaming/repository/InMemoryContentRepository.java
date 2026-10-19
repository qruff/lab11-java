package com.streaming.repository;

import com.streaming.domain.Content;
import com.streaming.domain.Genre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryContentRepository implements ContentRepository {

    private final Map<Integer, Content> storage = new LinkedHashMap<>();

    @Override
    public void save(Content content) {
        storage.put(content.getId(), content);
    }

    @Override
    public Optional<Content> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Content> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Content> findByGenre(Genre genre) {
        List<Content> result = new ArrayList<>();
        for (Content c : storage.values()) {
            if (c.hasGenre(genre)) {
                result.add(c);
            }
        }
        return result;
    }
}
