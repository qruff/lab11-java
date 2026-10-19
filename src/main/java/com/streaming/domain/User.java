package com.streaming.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {

    private final int id;
    private final String name;
    private final String email;
    private final Set<Genre> preferredGenres = new LinkedHashSet<>();

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<Genre> getPreferredGenres() {
        return preferredGenres;
    }

    public User likes(Genre genre) {
        preferredGenres.add(genre);
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
