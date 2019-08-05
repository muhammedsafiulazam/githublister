package com.muhammedsafiulazam.githublister.feature.repositorylist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammedsafiulazam.githublister.MainApplication
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.activity.BaseActivity
import com.muhammedsafiulazam.githublister.event.Event
import com.muhammedsafiulazam.githublister.feature.repositoryinfo.RepositoryInfoActivity
import com.muhammedsafiulazam.githublister.feature.repositorylist.event.RepositoryListEventType
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.muhammedsafiulazam.githublister.utils.RecyclerViewEndlessOnScrollListener
import kotlinx.android.synthetic.main.activity_repositorylist.*
import kotlinx.coroutines.channels.ReceiveChannel


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryListActivity : BaseActivity(), IRepositoryListListener {
    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private val mRepositoryList: MutableList<Repository> = mutableListOf()
    private val mRepositoryListAdapter: RepositoryListAdapter by lazy {
        RepositoryListAdapter(mRepositoryList, this)
    }

    private var mQuery: String = ""
    private lateinit var mSearchView: SearchView
    private lateinit var mSearchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_repositorylist)
        setActivityModel(RepositoryListActivityModel::class.java)

        updateMessage(null)
        updateLoader(false)
        updateSearchQuery(null)

        // Initialize recycler view.
        val linearLayoutManager = LinearLayoutManager(this)
        repositorylist_ryv_repositories.setLayoutManager(linearLayoutManager)
        repositorylist_ryv_repositories.adapter = mRepositoryListAdapter
        repositorylist_ryv_repositories.addOnScrollListener(object: RecyclerViewEndlessOnScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                loadRepositories()
            }
        })

        repositorylist_btn_retry.setOnClickListener(View.OnClickListener {
            onClickRetry()
        })

        repositorylist_btn_search_cancel.setOnClickListener(View.OnClickListener {
            onClickSearchCancel()
        })

        subscribeToEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_repositorylist, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchMenuItem = menu.findItem(R.id.repositorylist_search)
        mSearchView = mSearchMenuItem.actionView as SearchView
        mSearchView.queryHint = getString(R.string.repositorylist_search_hints)
        (mSearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchMenuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        mSearchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                mSearchView.clearFocus()
                val query = mSearchView.query.toString().trim()
                if (!TextUtils.equals(query, this@RepositoryListActivity.mQuery)) {
                    loadRepositories(query)
                }
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                mSearchView.requestFocus()
                return true
            }
        })

        return true
    }

    override fun onStart() {
        super.onStart()
        subscribeToEvents()
    }

    override fun onStop() {
        super.onStop()
        unsubscribeFromEvents()
    }

    private fun subscribeToEvents() {
        mReceiveChannel = MainApplication.getInstance().getEventManager().subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        MainApplication.getInstance().getEventManager().unsubscribe(mReceiveChannel)
    }

    fun updateLoader(show: Boolean) {
        if (show) {
            repositorylist_pgb_loader.visibility = VISIBLE
            updateMessage(null)
        } else {
            repositorylist_pgb_loader.visibility = GONE
        }
    }

    fun updateMessage(message: String?) {
        if (message != null) {
            repositorylist_txv_message.text = message
            repositorylist_txv_message.visibility = VISIBLE
            repositorylist_btn_retry.visibility = VISIBLE
            updateLoader(false)
        } else {
            repositorylist_txv_message.visibility = GONE
            repositorylist_btn_retry.visibility = GONE
        }
    }

    fun updateView(repositoryList: List<Repository>, query: String) {
        this.mRepositoryList.clear()
        this.mRepositoryList.addAll(repositoryList)

        this.repositorylist_ryv_repositories.post(Runnable {
            mRepositoryListAdapter.notifyDataSetChanged()
        })

        this.mQuery = query
        updateSearchQuery(this.mQuery)
    }

    fun updateSearchQuery(query: String?) {
        // Query.
        repositorylist_txv_search_query.text = getString(R.string.repositorylist_search_query, query)

        // Visibility.
        if (TextUtils.isEmpty(query)) {
            repositorylist_txv_search_query.visibility = GONE
            repositorylist_btn_search_cancel.visibility = GONE
        } else {
            repositorylist_txv_search_query.visibility = VISIBLE
            repositorylist_btn_search_cancel.visibility = VISIBLE
        }
    }

    private fun loadRepositories(query: String = this.mQuery) {
        val event: Event = Event(RepositoryListEventType.LOAD_DATA_REQUEST, query, null)
        MainApplication.getInstance().getEventManager().send(event)
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(RepositoryListEventType.LOAD_DATA_BUSY, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(RepositoryListEventType.LOAD_DATA_ERROR, event.type)) {
            updateMessage(event.data as String)
        } else if (TextUtils.equals(RepositoryListEventType.LOAD_DATA_RESPONSE, event.type)) {
            val data: Array<*> = event.data as Array<*>
            val repositoryList: List<Repository> = data.get(0) as List<Repository>
            val query: String = data.get(1) as String
            updateView(repositoryList, query)
        }
    }

    override fun onClickRepository(repository: Repository) {
        MainApplication.getInstance().getActivityManager().loadActivity(RepositoryInfoActivity::class.java, repository)
    }

    private fun onClickRetry() {
        loadRepositories()
    }

    private fun onClickSearchCancel() {
        mQuery = ""
        mSearchView.setQuery(mQuery, false)
        mSearchMenuItem.collapseActionView()
        mSearchView.clearFocus()
        loadRepositories()
    }

    override fun onDestroy() {
        unsubscribeFromEvents()
        super.onDestroy()
    }
}
