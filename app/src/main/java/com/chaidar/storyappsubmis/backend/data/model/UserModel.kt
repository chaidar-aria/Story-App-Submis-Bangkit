package com.chaidar.storyappsubmis.backend.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val tokenAuth: String,
    val isLogin: Boolean
) : Parcelable