package com.wildanpurnomo.timefighter.data.baseClass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val toastMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    internal fun setToastMessageAsync(toastMessage: String) {
        this.toastMessage.postValue(toastMessage)
    }

    internal fun setToastMessageSync(toastMessage: String) {
        this.toastMessage.value = toastMessage
    }

    internal fun getToastMessage(): LiveData<String> {
        return toastMessage
    }
}