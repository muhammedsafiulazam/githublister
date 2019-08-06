package com.muhammedsafiulazam.githublister.feature.repositoryinfo

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.activity.BaseActivity
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.event.IEventManager
import com.muhammedsafiulazam.githublister.feature.repositoryinfo.event.RepositoryInfoEventType
import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import kotlinx.android.synthetic.main.activity_repositoryinfo.*
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class RepositoryInfoActivity : BaseActivity() {
    private lateinit var mRepository: Repository

    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private val mContributorList: MutableList<Contributor> = mutableListOf()
    private val mContributorListAdapter: ContributorListAdapter by lazy {
        ContributorListAdapter(mContributorList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_repositoryinfo)
        setActivityModel(RepositoryInfoActivityModel::class.java)

        updateMessage(null)
        updateLoader(false)

        // Initialize recycler view.
        repositoryinfo_ryv_contributors.setLayoutManager(LinearLayoutManager(this))
        repositoryinfo_ryv_contributors.adapter = mContributorListAdapter

        mRepository = getData() as Repository
        updateView(mRepository)

        subscribeToEvents()
    }

    override fun onStart() {
        super.onStart()
        subscribeToEvents()
    }

    override fun onStop() {
        unsubscribeFromEvents()
        super.onStop()
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

    fun updateLoader(show: Boolean) {
        if (show) {
            repositoryinfo_pgb_loader.visibility = View.VISIBLE
            repositoryinfo_txv_message.visibility = View.GONE
        } else {
            repositoryinfo_pgb_loader.visibility = View.GONE
        }
    }

    fun updateMessage(message: String?) {
        if (message != null) {
            repositoryinfo_txv_message.text = message
            repositoryinfo_txv_message.visibility = View.VISIBLE
            repositoryinfo_pgb_loader.visibility = View.GONE
        } else {
            repositoryinfo_txv_message.visibility = View.GONE
        }
    }

    private fun updateView(repository: Repository) {

        repositoryinfo_txv_title_name.text = getString(R.string.repositoryinfo_title_name)
        repositoryinfo_txv_value_name.text = repository.name
        repositoryinfo_txv_title_description.text = getString(R.string.repositoryinfo_title_description)
        repositoryinfo_txv_value_description.text = repository.description
        repositoryinfo_txv_title_owner.text = getString(R.string.repositoryinfo_title_owner)
        repositoryinfo_txv_value_owner.text = repository.owner?.login
        repositoryinfo_txv_title_fork.text = getString(R.string.repositoryinfo_title_fork)
        if (repository.fork != null && repository.fork) {
            repositoryinfo_txv_value_fork.text = getString(R.string.repositoryinfo_value_fork_yes)
        } else {
            repositoryinfo_txv_value_fork.text = getString(R.string.repositoryinfo_value_fork_no)
        }
        repositoryinfo_txv_title_contributors.text = getString(R.string.repositoryinfo_title_contributors)
    }

    fun updateContributors(contributorList: List<Contributor>) {
        mContributorList.clear()
        mContributorList.addAll(contributorList)
        mContributorListAdapter.notifyDataSetChanged()
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(RepositoryInfoEventType.LOAD_DATA_BUSY, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(RepositoryInfoEventType.LOAD_DATA_ERROR, event.type)) {
            updateMessage(event.data as String)
        } else if (TextUtils.equals(RepositoryInfoEventType.LOAD_DATA_RESPONSE, event.type)) {
            val contributorList: List<Contributor> = event.data as List<Contributor>
            updateContributors(contributorList)
        }
    }

    override fun onDestroy() {
        unsubscribeFromEvents()
        super.onDestroy()
    }
}
