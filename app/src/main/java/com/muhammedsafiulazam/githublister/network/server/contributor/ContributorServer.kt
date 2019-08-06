package com.muhammedsafiulazam.githublister.network.server.contributor

import com.muhammedsafiulazam.githublister.addon.AddOn
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.network.retrofit.IRetrofitManager

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */


class ContributorServer : AddOn(), IContributorServer {
    // Contributor call.
    private val mContributorCall : IContributorCall by lazy {
        val retrofitManager: IRetrofitManager? = getAddOn(AddOnType.RETROFIT_MANAGER) as IRetrofitManager?
        retrofitManager!!.getRetrofit().create(IContributorCall::class.java)
    }

    /**
     * Get contributor calls.
     * @return repository calls
     */
    override fun getContributorCall() : IContributorCall {
        return mContributorCall
    }
}