package com.muhammedsafiulazam.githublister.network.server.repository

import com.muhammedsafiulazam.githublister.addon.AddOn
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.network.retrofit.IRetrofitManager

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryServer : AddOn(), IRepositoryServer {
    // Repository calls.
    private val mRepositoryCall : IRepositoryCall by lazy {
        val retrofitManager: IRetrofitManager? = getAddOn(AddOnType.RETROFIT_MANAGER) as IRetrofitManager?
        retrofitManager!!.getRetrofit().create(IRepositoryCall::class.java)
    }

    /**
     * Get repository calls.
     * @return repository calls
     */
    override fun getRepositoryCall() : IRepositoryCall {
        return mRepositoryCall
    }
}