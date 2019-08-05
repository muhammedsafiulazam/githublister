package com.muhammedsafiulazam.githublister.network.model.repository

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

@Parcelize
data class Search (
    @field:Json(name = "items") val items: List<Repository>?
) : Parcelable