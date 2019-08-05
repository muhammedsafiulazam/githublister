package com.muhammedsafiulazam.githublister.network.server.contributor

import com.muhammedsafiulazam.githublister.MainApplication

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ContributorServer {
    companion object {
        /**
         * Get contributor server.
         * @return contributor server
         */
        fun getContributorServer() : IContributorServer {
            return MainApplication.getInstance().getRetrofitManager().getRetrofit().create(IContributorServer::class.java)
        }
    }
}