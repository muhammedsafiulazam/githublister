package com.muhammedsafiulazam.githublister.network.service.repository

import com.muhammedsafiulazam.githublister.addon.AddOn
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.event.IEventManager
import com.muhammedsafiulazam.githublister.network.event.repository.RepositoryEventType
import com.muhammedsafiulazam.githublister.network.model.Error
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.muhammedsafiulazam.githublister.network.model.repository.Search
import com.muhammedsafiulazam.githublister.network.queue.IQueueManager
import com.muhammedsafiulazam.githublister.network.server.IServerManager
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryService : AddOn(), IRepositoryService {

    /**
     * Get repositories.
     * @param since repository id
     */
    override fun getRepositories(since: Int?) {
        // Server manager.
        val serverManager: IServerManager = getAddOn(AddOnType.SERVER_MANAGER) as IServerManager

        // Service call.
        val call: Call<List<Repository>> = serverManager.getRepositoryServer().getRepositoryCall().getRepositories(since)

        // Queue manager.
        val queueManager: IQueueManager = getAddOn(AddOnType.QUEUE_MANAGER) as IQueueManager

        // Push in queue.
        queueManager.execute(call as Call<Any>, callback = { response: Response<Any> ->
            var repositories: List<Repository>? = null
            var error: Error? = null

            if (response.isSuccessful()) {
                repositories = (response as Response<List<Repository>>).body()
            } else {
                error = Error(response.code(), response.errorBody()?.toString())
            }

            val event: Event = Event(RepositoryEventType.GET_REPOSITORIES, repositories, error)

            // Event manager.
            val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
            eventManager.send(event)
        })
    }

    /**
     * Search repositories.
     * @param query search query
     * @param page pagination
     */
    override fun searchRepositories(query: String?, page: Int?) {
        // Server manager.
        val serverManager: IServerManager = getAddOn(AddOnType.SERVER_MANAGER) as IServerManager

        // Service call.
        val call: Call<Search> = serverManager.getRepositoryServer().getRepositoryCall().searchRepositories(query, page)

        // Queue manager.
        val queueManager: IQueueManager = getAddOn(AddOnType.QUEUE_MANAGER) as IQueueManager

        // Push in queue.
        queueManager.execute(call as Call<Any>, callback = { response: Response<Any> ->
            var repositories: List<Repository>? = null
            var error: Error? = null

            if (response.isSuccessful()) {
                repositories = (response as Response<Search>).body()?.items
            } else {
                error = Error(response.code(), response.errorBody()?.toString())
            }

            val event: Event = Event(RepositoryEventType.SEARCH_REPOSITORIES, repositories, error)

            // Event manager.
            val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
            eventManager.send(event)
        })
    }
}