package com.muhammedsafiulazam.githublister.network.service.contributor

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IContributorService {
    /**
     * Get contributors.
     * @param repository repository full-name
     */
    fun getContributors(repository: String)
}