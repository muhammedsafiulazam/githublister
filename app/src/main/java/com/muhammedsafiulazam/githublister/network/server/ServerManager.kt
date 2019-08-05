package com.muhammedsafiulazam.githublister.network.server

import com.muhammedsafiulazam.githublister.network.server.contributor.ContributorServer
import com.muhammedsafiulazam.githublister.network.server.contributor.IContributorServer
import com.muhammedsafiulazam.githublister.network.server.repository.IRepositoryServer
import com.muhammedsafiulazam.githublister.network.server.repository.RepositoryServer

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ServerManager : IServerManager {

    private val mRepositoryServer: IRepositoryServer by lazy {
        RepositoryServer.getRepositoryServer()
    }

    private val mContributorServer: IContributorServer by lazy {
        ContributorServer.getContributorServer()
    }

    /**
     * Get repository server.
     * @return repository server
     */
    override fun getRepositoryServer(): IRepositoryServer {
        return mRepositoryServer
    }

    /**
     * Get contributor server.
     * @return contributor server
     */
    override fun getContributorServer(): IContributorServer {
        return mContributorServer
    }
}