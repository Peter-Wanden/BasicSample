/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.persistence.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.FtsOptions;

import static com.example.android.persistence.db.entity.ProductFtsEntity.FTS_TABLE;


// TODO-Fts-1: *** SECOND *** Create an Fts4 Entity
@Entity(tableName = FTS_TABLE)
@Fts4(contentEntity = ProductEntity.class,  // TODO-Fts-4: Associate this Fts entity with the
                                            // entity to search. Creates the Fts mapping table.
        tokenizer = FtsOptions.Tokenizer.UNICODE61, // TODO-Fts-5: Add tokenizer's (if required)
        tokenizerArgs = {"separators=."}, // TODO-Fts-6: Add any tokenizer args (if required)
        prefix = {2,3,4}) // TODO-Fts-7: Optimise for prefix searches (if required), etc.
public class ProductFtsEntity {

    public static final String FTS_TABLE = "productsFts";

    // TODO-Fts-2: Add all fields from the entity that will be searched (fields from the
    // ProductEntity.class in this case)
    private String name;
    private String description;

    public ProductFtsEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // TODO-Fts-3: Add getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
