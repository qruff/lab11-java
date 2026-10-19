package com.streaming.factory;

import com.streaming.domain.Content;

public interface ContentFactory {
    Content create(ContentData data);
}
