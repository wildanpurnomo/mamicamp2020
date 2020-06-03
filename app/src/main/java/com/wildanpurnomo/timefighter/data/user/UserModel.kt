package com.wildanpurnomo.timefighter.data.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val userId: String? = null,
    val username: String? = null,
    val email: String? = null,
    val maximumScore: Int? = null
) : Parcelable