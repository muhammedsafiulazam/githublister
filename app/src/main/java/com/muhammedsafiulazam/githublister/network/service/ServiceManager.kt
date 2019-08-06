package com.muhammedsafiulazam.githublister.network.service

import com.muhammedsafiulazam.githublister.addon.AddOn
import com.muhammedsafiulazam.githublister.network.service.contributor.ContributorService
import com.muhammedsafiulazam.githublister.network.service.contributor.IContributorService
import com.muhammedsafiulazam.githublister.network.service.repository.IRepositoryService
import com.muhammedsafiulazam.githublister.network.service.repository.RepositoryService

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ServiceManager : AddOn(), IServiceManager {

    private val mRepositoryService: IRepositoryService by lazy {
        val repositoryService = RepositoryService()
        repositoryService.addAddOns(getAddOns())
        repositoryService
    }
    private val mContributorService: IContributorService by lazy {
        val contributorService = ContributorService()
        contributorService.addAddOns(getAddOns())
        contributorService
    }

    /**
     * Get repository service.
     * @return repository service
     */
    override fun getRepositoryService(): IRepositoryService {
        return mRepositoryService
    }

    /**
     * Get contributor service.
     * @return contributor service
     */
    override fun getContributorService(): IContributorService {
        return mContributorService
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mRepositoryService.clearAddOns()
        mContributorService.clearAddOns()
        super.clearAddOns()
    }
}