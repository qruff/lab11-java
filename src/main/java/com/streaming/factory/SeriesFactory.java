package com.streaming.factory;

import com.streaming.domain.Content;
import com.streaming.domain.Series;

public class SeriesFactory implements ContentFactory {

    @Override
    public Content create(ContentData d) {
        return new Series(d.id, d.title, d.releaseYear, d.description, d.durationMinutes,
                d.seasons, d.episodes);
    }
}
