package com.muhammedsafiulazam.githublister.feature.repositoryinfo

import android.text.TextUtils
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.activity.BaseActivityModel
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.event.IEventManager
import com.muhammedsafiulazam.githublister.feature.repositoryinfo.event.RepositoryInfoEventType
import com.muhammedsafiulazam.githublister.network.event.contributor.ContributorEventType
import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.muhammedsafiulazam.githublister.network.service.IServiceManager
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class RepositoryInfoActivityModel : BaseActivityModel() {

    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private val mContributorList: MutableList<Contributor> = mutableListOf()

    override fun onCreateActivity() {
        super.onCreateActivity()
        subscribeToEvents()

        // Show loader.
        loadDataBusy(true)

        // Call for contributors.
        var repository: Repository? = getActivity()?.getData() as Repository
        val serviceManager: IServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        serviceManager.getContributorService()?.getContributors(repository?.fullname!!)
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
        val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mReceiveChannel = eventManager.subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
       eventManager.unsubscribe(mReceiveChannel)
    }

    private fun loadDataBusy(busy: Boolean) {
        val event = Event(RepositoryInfoEventType.LOAD_DATA_BUSY, busy, null)
        val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.send(event)
    }

    private fun loadDataError(error: String?) {
        val event = Event(RepositoryInfoEventType.LOAD_DATA_ERROR, error, null)
        val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.send(event)
    }

    private fun loadDataResponse(response: List<Contributor>) {
        val event = Event(RepositoryInfoEventType.LOAD_DATA_RESPONSE, response, null)
        val eventManager: IEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        eventManager.send(event)
    }

    fun onReceiveEvents(event: Event) {
        if (!TextUtils.equals(ContributorEventType.GET_CONTRIBUTORS, event.type))
            return

        // Hide loader.
        loadDataBusy(false)

        if (event.error != null) {
            // Show message.
            loadDataError(getActivity()?.getString(R.string.repositoryinfo_error_contributors))
        } else {

            mContributorList.clear()
            if (event.data != null) {
                this.mContributorList.addAll((event.data as List<Contributor>))
            }

            if (mContributorList.isEmpty()) {
                // Show message.
                loadDataError(getActivity()?.getString(R.string.repositoryinfo_no_contributors))
            } else {
                loadDataResponse(mContributorList)
            }
        }
    }

    override fun onDestroyActivity() {
        unsubscribeFromEvents()
        super.onDestroyActivity()
    }
}