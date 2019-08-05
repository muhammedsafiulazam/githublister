package com.muhammedsafiulazam.githublister.feature.repositorylist

import android.text.TextUtils
import com.muhammedsafiulazam.githublister.MainApplication
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.activity.BaseActivityModel
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.feature.repositorylist.event.RepositoryListEventType
import com.muhammedsafiulazam.githublister.network.event.repository.RepositoryEventType
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryListActivityModel : BaseActivityModel() {

    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private val mRepositoryList: MutableList<Repository> = mutableListOf()

    private var mIndex: Int = 0
    private var mQuery: String = ""

    override fun onCreateActivity() {
        super.onCreateActivity()
        subscribeToEvents()
        loadDataRequest()
    }

    override fun onStartActivity() {
        super.onStartActivity()
        subscribeToEvents()
    }

    override fun onStopActivity() {
        unsubscribeFromEvents()
        super.onStopActivity()
    }

    private fun subscribeToEvents() {
        mReceiveChannel = MainApplication.getInstance().getEventManager().subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        MainApplication.getInstance().getEventManager().unsubscribe(mReceiveChannel)
    }

    private fun loadDataBusy(busy: Boolean) {
        val event: Event = Event(RepositoryListEventType.LOAD_DATA_BUSY, busy, null)
        MainApplication.getInstance().getEventManager().send(event)
    }

    private fun loadDataError(error: String?) {
        val event: Event = Event(RepositoryListEventType.LOAD_DATA_ERROR, error, null)
        MainApplication.getInstance().getEventManager().send(event)
    }

    private fun loadDataResponse(response: Any?) {
        val event: Event = Event(RepositoryListEventType.LOAD_DATA_RESPONSE, response, null)
        MainApplication.getInstance().getEventManager().send(event)
    }

    fun loadDataRequest(query: String = "") {

        // New search.
        if (!TextUtils.equals(mQuery, query)) {
            clearRepositories()
            mQuery = query
        }

        // Show loader.
        loadDataBusy(true)

        if (!TextUtils.isEmpty(mQuery)) {
            MainApplication.getInstance().getServiceManager().getRepositoryService().searchRepositories(mQuery, mIndex)
        } else {
            MainApplication.getInstance().getServiceManager().getRepositoryService().getRepositories(mIndex)
        }
    }

    fun clearRepositories() {
        // Clear.
        mIndex = 0
        mQuery = ""
        mRepositoryList.clear()

        // Update view.
        loadDataResponse(arrayOf(mRepositoryList, mQuery))
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(RepositoryListEventType.LOAD_DATA_REQUEST, event.type)) {
            val query: String = event.data as String
            loadDataRequest(query)
        }
        else if (TextUtils.equals(RepositoryEventType.GET_REPOSITORIES, event.type) || TextUtils.equals(RepositoryEventType.SEARCH_REPOSITORIES, event.type)) {

            // Hide loader.
            loadDataBusy(false)

            if (event.error != null) {
                // Show message.
                loadDataError(getActivity()?.getString(R.string.repositorylist_error_repositories))
            } else {

                if (event.data != null) {

                    this.mRepositoryList.addAll(event.data as List<Repository>)

                    if (TextUtils.equals(RepositoryEventType.GET_REPOSITORIES, event.type)) {
                        // Next mIndex.
                        mIndex = mRepositoryList.last().id!!

                    }
                    else if (TextUtils.equals(RepositoryEventType.SEARCH_REPOSITORIES, event.type)) {
                        // Next mIndex.
                        mIndex = mIndex.inc()
                    }

                    // Update view.
                    loadDataResponse(arrayOf(mRepositoryList, mQuery))
                }
            }
        }
    }


    override fun onDestroyActivity() {
        unsubscribeFromEvents()
        super.onDestroyActivity()
    }
}