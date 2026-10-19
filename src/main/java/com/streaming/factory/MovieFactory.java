package com.streaming.factory;

import com.streaming.domain.Content;
import com.streaming.domain.Movie;

public class MovieFactory implements ContentFactory {

    @Override
    public Content create(ContentData d) {
        return new Movie(d.id, d.title, d.releaseYear, d.description, d.durationMinutes);
    }
}
