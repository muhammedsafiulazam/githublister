package com.muhammedsafiulazam.githublister.network.model.repository

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

@Parcelize
data class Owner (
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "avatar_url") val avatarUrl: String?
) : Parcelable