package com.wildanpurnomo.timefighter.utils

class Constants {
    object SharedPreferencesKeys {
        const val SHARED_PREF_NAME = "com.wildanpurnomo.timefighter.prefs"
        const val USER_ID_KEY = "user_id"
        const val USERNAME_KEY = "username"
        const val MAX_SCORE_KEY = "maximum_score"
        const val IS_FIRST_TIME = "is_first_time"
    }

    object FirestoreCollectionNames {
        const val COLLECTION_USERS = "users"
    }
}