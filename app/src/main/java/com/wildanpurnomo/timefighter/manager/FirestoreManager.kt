package com.wildanpurnomo.timefighter.manager

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object FirestoreManager {
    private val mFirestoreInstance = FirebaseFirestore.getInstance()

    fun addNewDocumentWithGeneratedID(collectionName: String, data: Any): Task<DocumentReference> {
        return mFirestoreInstance.collection(collectionName)
            .add(data)
    }

    fun addNewDocumentWithID(collectionName: String, documentId: String, data: Any): Task<Void> {
        return mFirestoreInstance.collection(collectionName).document(documentId)
            .set(data)
    }

    fun readAllCollection(collectionName: String): Task<QuerySnapshot> {
        return mFirestoreInstance.collection(collectionName)
            .get()
    }

    fun readWithQuery(collectionName: String, query: Pair<String, Any>): Task<QuerySnapshot> {
        return mFirestoreInstance.collection(collectionName).whereEqualTo(query.first, query.second)
            .get()
    }
}