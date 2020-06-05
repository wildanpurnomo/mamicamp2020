package com.wildanpurnomo.timefighter.data.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.wildanpurnomo.timefighter.data.baseClass.BaseViewModel
import com.wildanpurnomo.timefighter.manager.FirebaseAuthManager
import kotlin.math.absoluteValue

class UserViewModel(application: Application) : BaseViewModel(application) {

    private val mUserRemoteRepository = UserRemoteRepository()

    private val mUserLocalRepository = UserLocalRepository(application)

    private val successfulAuthSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getSuccessfulAuthSignal(): LiveData<Boolean> {
        return successfulAuthSignal
    }

    private val failedAuthSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getFailedAuthSignal(): LiveData<Boolean> {
        return failedAuthSignal
    }

    fun login(email: String, password: String) {
        FirebaseAuthManager.loginWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val loggedUser = FirebaseAuthManager.getCurrentUser()
                    mUserRemoteRepository.getUserByID(loggedUser?.uid.toString())
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val fetchedUser = document.toObject(UserModel::class.java)
                                mUserLocalRepository.cacheUserData(fetchedUser)
                            }
                            setToastMessageAsync("Login Successful.")
                            successfulAuthSignal.postValue(true)
                        }
                        .addOnFailureListener { exception ->
                            setToastMessageAsync(exception.message.toString())
                        }
                } else {
                    setToastMessageAsync(task.exception?.message.toString())
                    failedAuthSignal.postValue(true)
                }
            }
    }

    fun register(username: String, email: String, password: String) {
        FirebaseAuthManager.registerUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val registeredUser = FirebaseAuthManager.getCurrentUser()
                    val newUser = UserModel(
                        userId = registeredUser?.uid.toString(),
                        username = username,
                        email = email
                    )
                    mUserRemoteRepository.saveUserData(newUser)
                        .addOnSuccessListener {
                            mUserLocalRepository.cacheUserData(newUser)
                            setToastMessageAsync("Register Successful.")
                            successfulAuthSignal.postValue(true)
                        }
                        .addOnFailureListener { exception ->
                            setToastMessageAsync(exception.message.toString())
                        }
                } else {
                    setToastMessageAsync(task.exception?.message.toString())
                    failedAuthSignal.postValue(true)
                }
            }
    }

    fun authenticate() {
        val currentUser = FirebaseAuthManager.getCurrentUser()
        if (currentUser != null) {
            mUserRemoteRepository.getUserByID(currentUser.uid.toString())
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        mUserLocalRepository.cacheUserData(document.toObject(UserModel::class.java))
                        setToastMessageAsync("Authentication Successful.")
                        successfulAuthSignal.postValue(true)
                    }
                }
                .addOnFailureListener { exception ->
                    setToastMessageAsync(exception.message.toString())
                    failedAuthSignal.postValue(true)
                }
        } else {
            failedAuthSignal.postValue(true)
        }
    }

    private val successfulSignOutSignal: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getSuccessfulSignOutSignal(): LiveData<Boolean> {
        return successfulSignOutSignal
    }

    fun signOut() {
        FirebaseAuthManager.signOutUser()
        mUserLocalRepository.clearCache()
        successfulSignOutSignal.value = true
    }

    private val loggedUser: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }

    fun setLoggedUserData() {
        val userData = mUserLocalRepository.getCachedUserData()
        loggedUser.value = userData
    }

    fun getLoggedUser(): LiveData<UserModel> {
        return loggedUser
    }

    fun updateLoggedUserMaxScore(newScore: Int) {
        val userData = mUserLocalRepository.getCachedUserData()
        if (isMaxScore(userData.maximumScore, newScore)) {
            mUserRemoteRepository.updateUserScoreByID(userData.userId.toString(), newScore)
                .addOnSuccessListener {
                    mUserLocalRepository.updateCachedProperty("maximumScore", newScore)
                    setLoggedUserData()
                }
        }
    }

    private fun isMaxScore(currently: Int?, new: Int): Boolean {
        return when {
            currently == null -> {
                true
            }
            new > currently -> {
                true
            }
            else -> false
        }
    }

    private val leaderboardQuery: MutableLiveData<Query> by lazy {
        MutableLiveData<Query>(mUserRemoteRepository.getUsersSortByScore())
    }

    fun getLeaderboardQuery(): LiveData<Query> {
        return leaderboardQuery
    }
}