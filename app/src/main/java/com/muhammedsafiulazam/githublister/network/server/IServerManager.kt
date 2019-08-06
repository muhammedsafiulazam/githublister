package com.muhammedsafiulazam.githublister.network.server

import com.muhammedsafiulazam.githublister.addon.IAddOn
import com.muhammedsafiulazam.githublister.network.server.contributor.IContributorServer
import com.muhammedsafiulazam.githublister.network.server.repository.IRepositoryServer

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IServerManager : IAddOn {
    /**
     * Get repository server.
     * @return repository server
     */
    fun getRepositoryServer() : IRepositoryServer

    /**
     * Get contributor server.
     * @return contributor server
     */
    fun getContributorServer() : IContributorServer
}