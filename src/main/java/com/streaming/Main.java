package com.streaming;

import com.streaming.config.AppConfig;
import com.streaming.domain.Content;
import com.streaming.domain.Genre;
import com.streaming.domain.Playlist;
import com.streaming.domain.Rating;
import com.streaming.domain.User;
import com.streaming.factory.ContentData;
import com.streaming.factory.ContentFactory;
import com.streaming.factory.DocumentaryFactory;
import com.streaming.factory.MovieFactory;
import com.streaming.factory.SeriesFactory;
import com.streaming.recommendation.CollaborativeStrategy;
import com.streaming.recommendation.GenreBasedStrategy;
import com.streaming.recommendation.HybridStrategy;
import com.streaming.recommendation.PopularityStrategy;
import com.streaming.recommendation.RecommendationRequest;
import com.streaming.recommendation.RecommendationService;
import com.streaming.repository.ContentRepository;
import com.streaming.repository.InMemoryContentRepository;
import com.streaming.repository.InMemoryRatingRepository;
import com.streaming.repository.RatingRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        AppConfig config = AppConfig.getInstance();
        System.out.println("Приложение: " + config.getAppName());
        System.out.println("Лимит рекомендаций по умолчанию: " + config.getDefaultRecommendationLimit());

        
        ContentRepository contentRepo = new InMemoryContentRepository();
        RatingRepository ratingRepo = new InMemoryRatingRepository();

        Genre drama = new Genre(1, "Драма");
        Genre sciFi = new Genre(2, "Фантастика");
        Genre comedy = new Genre(3, "Комедия");
        Genre crime = new Genre(4, "Криминал");
        Genre action = new Genre(5, "Боевик");
        Genre history = new Genre(6, "История");

        ContentFactory movieFactory = new MovieFactory();
        ContentFactory seriesFactory = new SeriesFactory();
        ContentFactory documentaryFactory = new DocumentaryFactory();

        Content inception = movieFactory.create(
                new ContentData(1, "Начало", 2010, "Внедрение идей во сне", 148));
        inception.addGenre(sciFi).addGenre(action);

        Content shawshank = movieFactory.create(
                new ContentData(2, "Побег из Шоушенка", 1994, "Надежда в тюрьме", 142));
        shawshank.addGenre(drama).addGenre(crime);

        Content breakingBad = seriesFactory.create(
                new ContentData(3, "Во все тяжкие", 2008, "Учитель химии и криминал", 47)
                        .withSeries(5, 62));
        breakingBad.addGenre(drama).addGenre(crime);

        Content blackMirror = seriesFactory.create(
                new ContentData(4, "Чёрное зеркало", 2011, "Технологии и общество", 60)
                        .withSeries(6, 27));
        blackMirror.addGenre(sciFi).addGenre(drama);

        Content interstellar = movieFactory.create(
                new ContentData(5, "Интерстеллар", 2014, "Путешествие сквозь червоточину", 169));
        interstellar.addGenre(sciFi).addGenre(drama);

        Content ourPlanet = documentaryFactory.create(
                new ContentData(6, "Наша планета", 2019, "Природа Земли", 50).withTopic("Природа"));
        ourPlanet.addGenre(history);

        Content gentlemen = movieFactory.create(
                new ContentData(7, "Джентльмены", 2019, "Криминальная комедия", 113));
        gentlemen.addGenre(crime).addGenre(comedy);

        Content martian = movieFactory.create(
                new ContentData(8, "Марсианин", 2015, "Выживание на Марсе", 144));
        martian.addGenre(sciFi);

        Content office = seriesFactory.create(
                new ContentData(9, "Офис", 2005, "Будни офиса", 22).withSeries(9, 201));
        office.addGenre(comedy);

        List<Content> catalog = List.of(inception, shawshank, breakingBad, blackMirror,
                interstellar, ourPlanet, gentlemen, martian, office);
        catalog.forEach(contentRepo::save);
        System.out.println("Создано и сохранено единиц контента: " + catalog.size());

        User alice = new User(1, "Алиса", "alice@mail.ru").likes(sciFi).likes(drama);
        User victor = new User(3, "Виктор", "victor@mail.ru").likes(sciFi);
        User galina = new User(4, "Галина", "galina@mail.ru").likes(sciFi);
        User boris = new User(2, "Борис", "boris@mail.ru").likes(crime).likes(comedy);
        User dmitry = new User(5, "Дмитрий", "dmitry@mail.ru").likes(crime);

        rate(ratingRepo, alice, inception, 9);
        rate(ratingRepo, alice, interstellar, 10);
        rate(ratingRepo, alice, breakingBad, 6);
        rate(ratingRepo, victor, interstellar, 9);
        rate(ratingRepo, victor, martian, 9);
        rate(ratingRepo, victor, inception, 8);
        rate(ratingRepo, galina, martian, 8);
        rate(ratingRepo, galina, blackMirror, 9);
        rate(ratingRepo, galina, interstellar, 8);
        rate(ratingRepo, boris, gentlemen, 9);
        rate(ratingRepo, boris, office, 10);
        rate(ratingRepo, boris, breakingBad, 8);
        rate(ratingRepo, dmitry, shawshank, 10);
        rate(ratingRepo, dmitry, breakingBad, 9);
        rate(ratingRepo, dmitry, gentlemen, 7);

        RecommendationRequest request = RecommendationRequest.builder(alice)
                .minYear(1990)
                .limit(config.getDefaultRecommendationLimit())
                .build();
        System.out.println("Запрос: пользователь=Алиса, minYear=1990, limit="
                + config.getDefaultRecommendationLimit());

        RecommendationService service = new RecommendationService(contentRepo, new GenreBasedStrategy(ratingRepo));
        printRecommendations(service, request);

        service.setStrategy(new PopularityStrategy(ratingRepo));   // смена алгоритма на лету
        printRecommendations(service, request);

        service.setStrategy(new CollaborativeStrategy(ratingRepo));
        printRecommendations(service, request);

        service.setStrategy(new HybridStrategy(ratingRepo));
        printRecommendations(service, request);

        Playlist series = new Playlist("Сериалы на вечер")
                .add(blackMirror)
                .add(breakingBad);
        Playlist evening = new Playlist("Вечер фантастики")
                .add(inception)
                .add(interstellar)
                .add(series);
        evening.display("");
        System.out.println("Итоговая длительность подборки: " + evening.getTotalDuration().toHours() + " ч");

        List<Content> sciFiContent = contentRepo.findByGenre(sciFi);
        System.out.println("Фантастика в каталоге: " + sciFiContent);

    }

    private static void rate(RatingRepository repo, User user, Content content, int value) {
        repo.save(new Rating(user, content, value));
    }

    private static void printRecommendations(RecommendationService service, RecommendationRequest request) {
        System.out.println("\nСтратегия: " + service.currentStrategyName());
        List<Content> result = service.recommend(request);
        int position = 1;
        for (Content c : result) {
            System.out.printf("  %d) %s%n", position++, c);
        }
    }
}
