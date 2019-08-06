package com.muhammedsafiulazam.githublister.network.server

import com.muhammedsafiulazam.githublister.addon.AddOn
import com.muhammedsafiulazam.githublister.network.server.contributor.ContributorServer
import com.muhammedsafiulazam.githublister.network.server.contributor.IContributorServer
import com.muhammedsafiulazam.githublister.network.server.repository.IRepositoryServer
import com.muhammedsafiulazam.githublister.network.server.repository.RepositoryServer

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ServerManager : AddOn(), IServerManager {

    private val mRepositoryServer: IRepositoryServer by lazy {
        val repositoryServer = RepositoryServer()
        repositoryServer.addAddOns(getAddOns())
        repositoryServer
    }

    private val mContributorServer: IContributorServer by lazy {
        val contributorServer = ContributorServer()
        contributorServer.addAddOns(getAddOns())
        contributorServer
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

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mRepositoryServer.clearAddOns()
        mContributorServer.clearAddOns()
        super.clearAddOns()
    }
}