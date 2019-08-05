package com.muhammedsafiulazam.githublister.network.service

import com.muhammedsafiulazam.githublister.network.service.contributor.IContributorService
import com.muhammedsafiulazam.githublister.network.service.repository.IRepositoryService

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IServiceManager {
    /**
     * Get repository service.
     * @return repository service
     */
    fun getRepositoryService() : IRepositoryService

    /**
     * Get contributor service.
     * @return contributor service
     */
    fun getContributorService() : IContributorService
}