package com.muhammedsafiulazam.githublister.network.service.contributor

import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IContributorService : IAddOn {
    /**
     * Get contributors.
     * @param repository repository full-name
     */
    fun getContributors(repository: String)
}