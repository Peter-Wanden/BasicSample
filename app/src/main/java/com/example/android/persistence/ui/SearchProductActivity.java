package com.example.android.persistence.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.persistence.R;
import com.example.android.persistence.provider.RecentSuggestionsProvider;
import com.example.android.persistence.viewmodel.SearchViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

/**
 * TODO - Add the following:
 * - Search widget - Required if using an action bar
 * - Adding a submit button
 * - Query refinement
 * - Toggle search box visibility
 * - Using both the search widget and dialog
 * - Adding voice search
 */

// TODO-S4: Create a searchable_product activity.
// Performs searches and presents the results
public class SearchProductActivity extends AppCompatActivity {

    public static final String TAG = SearchProductActivity.class.getSimpleName();
    private SearchViewModel mModel;
    private MutableLiveData<String> mOriginatingClass = new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        setTitle("Searchable Activity");

        mModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mModel.getOriginatingClass().observe(this, s -> mOriginatingClass.setValue(s));
        mModel.getQuery().observe(this, s -> mQuery.setValue(s));


        // TODO-S8: Receive and handle intent
        handleIntent(getIntent());

    }

    // TODO-S10: Handle new search intents while SearchActivity is already at the top of the stack
    @Override
    protected void onNewIntent(Intent intent) {
        // setIntent() replaces the intent received when the activity started with the new intent
        setIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        // Gets the originating class contextual data
        Bundle b = intent.getBundleExtra(SearchManager.APP_DATA);
        if (b != null) {
            mModel.setOriginatingClass(b.getString(SearchProductActivity.TAG));
            Log.e(TAG, "Originating class is: " + b.getString(SearchProductActivity.TAG));
        }

        // Verifies the intent action
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // TODO-S5a: A suggestion has not been selected, so perform a full search

            mModel.setQuery(intent.getStringExtra(SearchManager.QUERY));
            Log.e(TAG, "Intent action is SEARCH");
            Log.e(TAG, "Query is: " + intent.getStringExtra(SearchManager.QUERY));

            // TODO-Rs3: Save the query to the recent query database
            String originatingClass = null;
            if (b != null) {
                originatingClass = b.getString(SearchProductActivity.TAG);
            }
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSuggestionsProvider.AUTHORITY, RecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, originatingClass);



            // Gets the search query
            mModel.setQuery(intent.getStringExtra(SearchManager.QUERY));


        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Log.e(TAG, "Intent action is VIEW - query is: " );
            Log.e(TAG, "Intent extra data is: " + intent.getData());
            // TODO-S5b: A suggestion has been selected, so go to item view
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO-SA1: Inflate the options menu
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);

        // TODO-SA2: NOT REQUIRED - Associate searchable_product configuration with the SearchView
        return true;
    }

    // TODO-SA2a: Call onSearchRequested when search icon is selected in the AppBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return false;
        }
    }

    // TODO-SA2b: -
    @Override
    public boolean onSearchRequested() {
        Log.e(TAG, "On search requested called.");
        Bundle b = new Bundle();
        b.putString(SearchProductActivity.TAG, SearchProductActivity.TAG);
        startSearch(null, false, b, false);
        return true;
    }
}
