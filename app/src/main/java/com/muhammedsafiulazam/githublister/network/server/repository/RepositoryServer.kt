package com.muhammedsafiulazam.githublister.network.server.repository

import com.muhammedsafiulazam.githublister.Knowledge

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
            return Knowledge.getRetrofitManager().getRetrofit().create(IRepositoryServer::class.java)
        }
    }
}