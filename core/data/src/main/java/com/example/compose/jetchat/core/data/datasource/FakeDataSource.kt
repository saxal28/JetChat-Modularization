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

package com.example.compose.jetchat.core.data.datasource

import com.example.compose.jetchat.core.data.model.Message
import com.example.compose.jetchat.core.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeDataSource @Inject constructor() {

    private val initialMessages = listOf(
        Message(
            "me",
            "Check it out!",
            "8:07 PM"
        ),
        Message(
            "me",
            "Thank you!$EMOJI_PINK_HEART",
            "8:06 PM",
            1 // Placeholder for sticker image
        ),
        Message(
            "Taylor Brooks",
            "You can use all the same stuff",
            "8:05 PM"
        ),
        Message(
            "Taylor Brooks",
            "@aliconors Take a look at the `Flow.collectAsStateWithLifecycle()` APIs",
            "8:05 PM"
        ),
        Message(
            "John Glenn",
            "Compose newbie as well $EMOJI_FLAMINGO, have you looked at the JetNews sample? " +
                "Most blog posts end up out of date pretty fast but this sample is always up to " +
                "date and deals with async data loading (it's faked but the same idea " +
                "applies) $EMOJI_POINTS https://goo.gle/jetnews",
            "8:04 PM"
        ),
        Message(
            "me",
            "Compose newbie: I've scourged the internet for tutorials about async data " +
                "loading but haven't found any good ones $EMOJI_MELTING $EMOJI_CLOUDS. " +
                "What's the recommended way to load async data and emit composable widgets?",
            "8:03 PM"
        ),
        Message(
            "Shangeeth Sivan",
            "Does anyone know about Glance Widgets its the new way to build widgets in Android!",
            "8:08 PM"
        ),
        Message(
            "Taylor Brooks",
            "Wow! I never knew about Glance Widgets when was this added to the android ecosystem",
            "8:10 PM"
        ),
        Message(
            "John Glenn",
            "Yeah its seems to be pretty new!",
            "8:12 PM"
        ),
    )

    fun getInitialMessages(): List<Message> = initialMessages

    fun getUnreadMessages(): List<Message> = initialMessages.filter { it.author != "me" }

    fun getColleagueProfile(): User = User(
        userId = "12345",
        photo = 0, // Will be provided by repository
        name = "Taylor Brooks",
        status = "Away",
        displayName = "taylor",
        position = "Senior Android Dev at Openlane",
        twitter = "twitter.com/taylorbrookscodes",
        timeZone = "12:25 AM local time (Eastern Daylight Time)",
        commonChannels = "2"
    )

    fun getMeProfile(): User = User(
        userId = "me",
        photo = 0, // Will be provided by repository
        name = "Ali Conors",
        status = "Online",
        displayName = "aliconors",
        position = "Senior Android Dev at Yearin\nGoogle Developer Expert",
        twitter = "twitter.com/aliconors",
        timeZone = "In your timezone",
        commonChannels = null
    )

    companion object {
        // EMOJI 15
        const val EMOJI_PINK_HEART = "\uD83E\uDE77"

        // EMOJI 14 🫠
        const val EMOJI_MELTING = "\uD83E\uDEE0"

        // ANDROID 13.1 😶‍🌫️
        const val EMOJI_CLOUDS = "\uD83D\uDE36\u200D\uD83C\uDF2B️"

        // ANDROID 12.0 🦩
        const val EMOJI_FLAMINGO = "\uD83E\uDDA9"

        // ANDROID 12.0  👉
        const val EMOJI_POINTS = " \uD83D\uDC49"
    }
}