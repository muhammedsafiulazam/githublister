package com.muhammedsafiulazam.githublister.network.service.repository

import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRepositoryService : IAddOn {
    /**
     * Get repositories.
     * @param since repository id
     */
    fun getRepositories(since: Int?)

    /**
     * Search repositories.
     * @param query search query
     * @param page pagination
     */
    fun searchRepositories(query: String?, page: Int?)
}