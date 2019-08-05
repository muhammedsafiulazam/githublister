package com.muhammedsafiulazam.githublister.network.service.contributor

import com.muhammedsafiulazam.githublister.MainApplication
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.network.event.contributor.ContributorEventType
import com.muhammedsafiulazam.githublister.network.model.Error
import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor
import com.muhammedsafiulazam.githublister.network.server.IServerManager
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ContributorService : IContributorService {
    /**
     * Get contributors.
     * @param repository repository full-name
     */
    override fun getContributors(repository: String) {
        // Server manager.
        val serverManager: IServerManager = MainApplication.getInstance().getServerManager()

        // Service call.
        val call: Call<List<Contributor>> = serverManager.getContributorServer().getContributors(repository)

        // Queue manager.
        MainApplication.getInstance().getQueueManager().execute(call as Call<Any>, callback = { response: Response<Any> ->
            var contributors: List<Contributor>? = null
            var error: Error? = null

            if (response.isSuccessful()) {
                contributors = (response as Response<List<Contributor>>).body()
            } else {
                error = Error(response.code(), response.errorBody()?.toString())
            }

            val event: Event = Event(ContributorEventType.GET_CONTRIBUTORS, contributors, error)
            MainApplication.getInstance().getEventManager().send(event)
        })
    }
}