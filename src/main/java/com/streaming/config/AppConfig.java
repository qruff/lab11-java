package com.streaming.config;

public final class AppConfig {

    private final String appName = "Streaming Catalog";
    private final int defaultRecommendationLimit = 5;
    private final int likeThreshold = 7;

    private AppConfig() {
    }

    private static class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }

    public String getAppName() {
        return appName;
    }

    public int getDefaultRecommendationLimit() {
        return defaultRecommendationLimit;
    }

    public int getLikeThreshold() {
        return likeThreshold;
    }
}
