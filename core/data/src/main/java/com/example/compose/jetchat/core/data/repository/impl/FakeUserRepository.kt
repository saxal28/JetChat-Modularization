/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetchat.core.data.repository.impl

import com.example.compose.jetchat.core.data.datasource.FakeDataSource
import com.example.compose.jetchat.core.data.model.User
import com.example.compose.jetchat.core.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserRepository @Inject constructor(
    private val dataSource: FakeDataSource
) : UserRepository {

    override fun getUser(userId: String): User? {
        return when (userId) {
            "me" -> getMeProfile()
            else -> getColleagueProfile()
        }
    }

    override fun getMeProfile(): User {
        return dataSource.getMeProfile()
    }

    override fun getColleagueProfile(): User {
        return dataSource.getColleagueProfile()
    }
}