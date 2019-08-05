package com.muhammedsafiulazam.githublister.network.model.repository

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

@Parcelize
data class Repository (
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "full_name") val fullname: String?,
    @field:Json(name = "owner") val owner: Owner?,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "fork") val fork: Boolean?
) : Parcelable