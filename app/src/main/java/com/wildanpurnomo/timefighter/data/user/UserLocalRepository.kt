package com.wildanpurnomo.timefighter.data.user

import android.app.Application
import com.wildanpurnomo.timefighter.data.baseClass.BaseLocalRepository
import com.wildanpurnomo.timefighter.utils.Constants

class UserLocalRepository(application: Application) : BaseLocalRepository(application) {
    fun cacheUserData(userData: UserModel) {
        mSharedPreferencesManager.saveString(
            Constants.SharedPreferencesKeys.USER_ID_KEY,
            userData.userId.toString()
        )
        mSharedPreferencesManager.saveString(
            Constants.SharedPreferencesKeys.USERNAME_KEY,
            userData.username.toString()
        )
        mSharedPreferencesManager.saveInt(
            Constants.SharedPreferencesKeys.MAX_SCORE_KEY,
            userData.maximumScore ?: 0
        )
    }

    fun getCachedUserData(): UserModel {
        val userId =
            mSharedPreferencesManager.getValueString(Constants.SharedPreferencesKeys.USER_ID_KEY)
        val username =
            mSharedPreferencesManager.getValueString(Constants.SharedPreferencesKeys.USERNAME_KEY)
        val maxScore =
            mSharedPreferencesManager.getValueInt(Constants.SharedPreferencesKeys.MAX_SCORE_KEY)

        return UserModel(
            userId = userId.toString(),
            username = username.toString(),
            maximumScore = maxScore
        )
    }

    fun updateCachedProperty(propertyName: String, value: Any) {
        when (propertyName) {
            "userId" -> mSharedPreferencesManager.saveString(
                Constants.SharedPreferencesKeys.USER_ID_KEY,
                value as String
            )
            "username" -> mSharedPreferencesManager.saveString(
                Constants.SharedPreferencesKeys.USERNAME_KEY,
                value as String
            )
            "maximumScore" -> mSharedPreferencesManager.saveInt(
                Constants.SharedPreferencesKeys.MAX_SCORE_KEY,
                value as Int
            )
            "isFirstTime" -> mSharedPreferencesManager.saveBoolean(
                Constants.SharedPreferencesKeys.IS_FIRST_TIME,
                value as Boolean
            )
        }
    }

    fun getIsFirstTime(): Boolean {
        return mSharedPreferencesManager.getValueBoolean(Constants.SharedPreferencesKeys.IS_FIRST_TIME)
            ?: true
    }

    fun clearCache() {
        mSharedPreferencesManager.clearSharedPreference()
    }
}