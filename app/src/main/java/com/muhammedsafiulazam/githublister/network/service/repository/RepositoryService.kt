package com.muhammedsafiulazam.githublister.network.service.repository

import com.muhammedsafiulazam.githublister.Knowledge
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.network.event.repository.RepositoryEventType
import com.muhammedsafiulazam.githublister.network.model.Error
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.muhammedsafiulazam.githublister.network.model.repository.Search
import com.muhammedsafiulazam.githublister.network.server.IServerManager
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryService : IRepositoryService {

    /**
     * Get repositories.
     * @param since repository id
     */
    override fun getRepositories(since: Int?) {
        // Server manager.
        val serverManager: IServerManager = Knowledge.getServerManager()

        // Service call.
        val call: Call<List<Repository>> = serverManager.getRepositoryServer().getRepositories(since)

        // Queue manager.
        Knowledge.getQueueManager().execute(call as Call<Any>, callback = { response: Response<Any> ->
            var repositories: List<Repository>? = null
            var error: Error? = null

            if (response.isSuccessful()) {
                repositories = (response as Response<List<Repository>>).body()
            } else {
                error = Error(response.code(), response.errorBody()?.toString())
            }

            val event: Event = Event(RepositoryEventType.GET_REPOSITORIES, repositories, error)
            Knowledge.getEventManager().send(event)
        })
    }

    /**
     * Search repositories.
     * @param query search query
     * @param page pagination
     */
    override fun searchRepositories(query: String?, page: Int?) {
        // Server manager.
        val serverManager: IServerManager = Knowledge.getServerManager()

        // Service call.
        val call: Call<Search> = serverManager.getRepositoryServer().searchRepositories(query, page)

        // Queue manager.
        Knowledge.getQueueManager().execute(call as Call<Any>, callback = { response: Response<Any> ->
            var repositories: List<Repository>? = null
            var error: Error? = null

            if (response.isSuccessful()) {
                repositories = (response as Response<Search>).body()?.items
            } else {
                error = Error(response.code(), response.errorBody()?.toString())
            }

            val event: Event = Event(RepositoryEventType.SEARCH_REPOSITORIES, repositories, error)
            Knowledge.getEventManager().send(event)
        })
    }
}