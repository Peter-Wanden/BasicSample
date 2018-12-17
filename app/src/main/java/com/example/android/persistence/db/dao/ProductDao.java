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

package com.example.android.persistence.db.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.android.persistence.db.entity.ProductEntity;

import java.util.List;

import static android.app.SearchManager.SUGGEST_COLUMN_INTENT_DATA;
import static android.app.SearchManager.SUGGEST_COLUMN_TEXT_1;
import static android.app.SearchManager.SUGGEST_COLUMN_TEXT_2;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> products);

    @Query("select * from products where id = :productId")
    LiveData<ProductEntity> loadProduct(int productId);

    @Query("select * from products where id = :productId")
    ProductEntity loadProductSync(int productId);

    // TODO-Fts-8: Provide search SQL to return a cursor with correct column headings
    // See: https://developer.android.com/guide/topics/search/adding-custom-suggestions#HandlingSuggestionQuery
    @SuppressWarnings("AndroidUnresolvedRoomSqlReference") // Only add this once tested
    @Query("SELECT products.id AS _id, products.name AS " +
            SUGGEST_COLUMN_TEXT_1 + ", products.description AS " + SUGGEST_COLUMN_TEXT_2 + ", " +
            "products.id AS " + SUGGEST_COLUMN_INTENT_DATA +
            " FROM products JOIN productsFts ON (products.id = productsFts.rowid) " +
            "WHERE productsFts MATCH :query")
    Cursor searchAllProducts(String query);
}
