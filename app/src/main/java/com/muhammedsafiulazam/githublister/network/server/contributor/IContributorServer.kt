package com.muhammedsafiulazam.githublister.network.server.contributor

import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IContributorServer : IAddOn {

    /**
     * Get contributor call.
     * @return contributor call
     */
    fun getContributorCall() : IContributorCall
}