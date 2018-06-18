package com.enihsyou.androidexamination.adapter;

import android.content.SearchRecentSuggestionsProvider;

public class MovieSearchProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.enihsyou.androidexamination.adapter.MovieSearchProvider";

    public final static int MODE = DATABASE_MODE_QUERIES;

    public MovieSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
