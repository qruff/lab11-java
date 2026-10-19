package com.streaming.domain;

import java.time.Duration;

public interface CatalogItem {
    String getTitle();

    Duration getTotalDuration();

    void display(String indent);
}
