package com.muhammedsafiulazam.githublister.network.server.contributor

import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IContributorServer {
    /**
     * Get contributors.
     * @param repository repository full-name
     * @return list of contributors
     */
    @GET("repos/{fullname}/contributors")
    fun getContributors(@Path("fullname", encoded = true) repository: String?) : Call<List<Contributor>>
}