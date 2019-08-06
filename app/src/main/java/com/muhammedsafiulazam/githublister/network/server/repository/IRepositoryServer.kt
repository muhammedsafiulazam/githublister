package com.muhammedsafiulazam.githublister.network.server.repository

import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRepositoryServer : IAddOn {

    /**
     * Get repository call.
     * @return repository call
     */
    fun getRepositoryCall() : IRepositoryCall
}