package com.example.android.persistence.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.persistence.BasicApp;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// TODO-C1: Create a content provider class
// Now that we have a data source with the Fts implementation and a Search configuration its time
// to build a content provider for search suggestions and to connect the data with the
// SearchActivity.
// see: https://github.com/googlesamples/android-architecture-components/tree/master/PersistenceContentProviderSample
public class SuggestionsProvider extends ContentProvider {

    private static final String TAG = SuggestionsProvider.class.getSimpleName();

    // TODO-C2: Define the AUTHORITY (see app.Constants)
    // The authority of this content provider
    public static String AUTHORITY = "com.example.android.persistence.provider.SuggestionsProvider";

    // TODO-C3: Add the Uri matcher and associated query paths
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int SEARCH_SUGGEST = 1;

    static {
        MATCHER.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    // TODO-C4: Implement the query method
    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {

        // Match the Uri
        final int matchCode = MATCHER.match(uri);
        if (matchCode == SEARCH_SUGGEST) {

            if (selectionArgs == null) {
                throw new IllegalArgumentException(
                        "selectionArgs must be provided for the Uri: " + uri);
            }
            // Get the cursor async and append a wildcard
            BasicApp app = new BasicApp();
            return app.getRepository().searchProducts(selectionArgs[0] + "*");
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    // TODO-C5: Implement the getType() method
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    // Non of the following are required for search suggestions, but stubs must be exposed.
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String s,
                      @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String s,
                      @Nullable String[] strings) {
        return 0;
    }
}
