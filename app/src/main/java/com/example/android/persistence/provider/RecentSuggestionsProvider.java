package com.example.android.persistence.provider;

import android.content.SearchRecentSuggestionsProvider;

// TODO-RS-1: Add a recent suggestions provider class
// See: https://developer.android.com/guide/topics/search/adding-recent-query-suggestions
public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static String AUTHORITY =
            "com.example.android.persistence.provider.RecentSuggestionsProvider";

    public static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public RecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
