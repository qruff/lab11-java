package com.streaming.factory;

import com.streaming.domain.Content;
import com.streaming.domain.Documentary;

public class DocumentaryFactory implements ContentFactory {

    @Override
    public Content create(ContentData d) {
        return new Documentary(d.id, d.title, d.releaseYear, d.description, d.durationMinutes,
                d.topic);
    }
}
