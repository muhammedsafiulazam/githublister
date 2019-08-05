package com.muhammedsafiulazam.githublister.network.retrofit

import retrofit2.Retrofit

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRetrofitManager {
    /**
     * Get retrofit.
     * @return retrofit
     */
    fun getRetrofit() : Retrofit
}