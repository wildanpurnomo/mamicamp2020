package com.wildanpurnomo.timefighter.data.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.wildanpurnomo.timefighter.data.user.UserModel

class LeaderboardViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        stopQueryListener()
    }

    private val leaderboardList: MutableLiveData<ArrayList<LeaderboardModel>> by lazy {
        MutableLiveData<ArrayList<LeaderboardModel>>()
    }

    fun getLeaderboardList(): LiveData<ArrayList<LeaderboardModel>> {
        return leaderboardList
    }

    private val toastMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getToastMsg(): LiveData<String> {
        return toastMessage
    }

    private val queryListener: MutableLiveData<ListenerRegistration> by lazy {
        MutableLiveData<ListenerRegistration>()
    }

    private val query: MutableLiveData<Query> by lazy {
        MutableLiveData<Query>()
    }

    fun setQueryListener(query: Query) {
        this.query.value = query
        queryListener.value = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                toastMessage.value = e.message
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val leaderboardList = ArrayList<LeaderboardModel>()
                snapshot.forEach {
                    val userData = it.toObject(UserModel::class.java)
                    val leaderboardInstance = LeaderboardModel(
                        username = userData.username,
                        score = userData.maximumScore
                    )
                    leaderboardList.add(leaderboardInstance)
                }
                Log.d("tesJalanGug", "$leaderboardList")
                this.leaderboardList.value = leaderboardList
            }
        }
    }

    private fun stopQueryListener() {
        queryListener.value?.remove()
    }
}