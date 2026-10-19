package com.streaming.repository;

import com.streaming.domain.Content;
import com.streaming.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface ContentRepository {

    void save(Content content);

    Optional<Content> findById(int id);

    List<Content> findAll();

    List<Content> findByGenre(Genre genre);
}
