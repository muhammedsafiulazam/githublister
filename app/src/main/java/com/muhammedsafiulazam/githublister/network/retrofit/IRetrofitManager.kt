package com.muhammedsafiulazam.githublister.network.retrofit

import com.muhammedsafiulazam.githublister.addon.IAddOn
import retrofit2.Retrofit

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRetrofitManager : IAddOn {
    /**
     * Get retrofit.
     * @return retrofit
     */
    fun getRetrofit() : Retrofit
}