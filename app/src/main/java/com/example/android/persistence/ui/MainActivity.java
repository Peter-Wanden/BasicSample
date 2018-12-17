/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence.ui;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.persistence.R;
import com.example.android.persistence.model.Product;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // TODO-S2a: Activate the search dialog when the user starts typing on the keyboard
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit();
        }
    }

    /** Shows the product detail fragment */
    public void show(Product product) {

        ProductFragment productFragment = ProductFragment.forProduct(product.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container,
                        productFragment, null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO-S2: - Inflate the options menu
        getMenuInflater().inflate(R.menu.activity_search_menu, menu);
        // TODO-S5: - Associate searchable_product configuration with the SearchView
        // *** ONLY REQUIRED IF USING SEARCH WIDGET ***
        //        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView sv = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//        if (sm != null) {
//            sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
//        }
//        sv.setIconifiedByDefault(false);
        return true;
    }

    // TODO-S11: - Override onOptionsItemSelected and onSearchRequested() to add additional data to
    // intent. Do this for EVERY activity that initiate search.
    // Also, if you have several activities that point to a single SearchProductActivity, override
    // these methods to add identifiable contextual data.
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
    @Override
    public boolean onSearchRequested() {
        // Add anything here you want to send to the search activity for this class
        Bundle appData = new Bundle();
        appData.putString(SearchProductActivity.TAG, MainActivity.TAG);
        startSearch(null, false, appData, false);
        return true;
    }
}
