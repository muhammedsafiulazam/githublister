package com.muhammedsafiulazam.githublister.network.server.repository

import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.muhammedsafiulazam.githublister.network.model.repository.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRepositoryCall {
    /**
     * Get repositories.
     * @param since repository id
     * @return list of repositories
     */
    @GET("repositories?")
    fun getRepositories(@Query("since") since: Int?) : Call<List<Repository>>

    /**
     * Search repositories.
     * @param query search query
     * @param page pagination
     * @return search result
     */
    @GET("search/repositories?")
    fun searchRepositories(@Query("q") query: String?, @Query("page") page: Int?) : Call<Search>
}