package com.wildanpurnomo.timefighter.data.baseClass

import android.app.Application
import com.wildanpurnomo.timefighter.manager.SharedPreferencesManager

open class BaseLocalRepository(application: Application) {
    internal val mSharedPreferencesManager = SharedPreferencesManager(application)
}