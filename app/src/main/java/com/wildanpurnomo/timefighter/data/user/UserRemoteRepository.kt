package com.wildanpurnomo.timefighter.data.user

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.wildanpurnomo.timefighter.manager.FirestoreManager
import com.wildanpurnomo.timefighter.utils.Constants

class UserRemoteRepository {

    fun saveUserData(userData: UserModel): Task<Void> {
        return FirestoreManager.addNewDocumentWithID(
            Constants.FirestoreCollectionNames.COLLECTION_USERS,
            userData.userId.toString(),
            userData
        )
    }

    fun getUserByID(userID: String): Task<QuerySnapshot> {
        return FirestoreManager.readWithQuery(
            Constants.FirestoreCollectionNames.COLLECTION_USERS,
            Pair("userId", userID)
        )
    }

    fun updateUserScoreByID(userID: String, score: Int): Task<Void> {
        return FirestoreManager.updateDocumentById(
            Constants.FirestoreCollectionNames.COLLECTION_USERS,
            userID,
            Pair("maximumScore", score)
        )
    }
}