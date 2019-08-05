package com.muhammedsafiulazam.githublister.network.server.repository

import com.muhammedsafiulazam.githublister.MainApplication

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryServer {
    companion object {
        /**
         * Get repository server.
         * @return repository server
         */
        fun getRepositoryServer() : IRepositoryServer {
            return MainApplication.getInstance().getRetrofitManager().getRetrofit().create(IRepositoryServer::class.java)
        }
    }
}