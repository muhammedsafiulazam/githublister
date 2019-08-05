package com.muhammedsafiulazam.githublister.feature.repositorylist

import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.network.model.repository.Repository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryViewHolder(view: View, userListListener: IRepositoryListListener) : RecyclerView.ViewHolder(view){
    private var mView: View? = null
    private var mRepository: Repository? = null
    private var mTxvName: AppCompatTextView? = null
    private var mTxvDescription: AppCompatTextView? = null
    private var mTxvOwner: AppCompatTextView? = null
    private var mImvPicture: AppCompatImageView? = null
    private var mPgbPicture: ProgressBar? = null

    init {
        mView = view
        mTxvName = view.findViewById(R.id.repository_txv_name)
        mTxvDescription = view.findViewById(R.id.repository_txv_description)
        mTxvOwner = view.findViewById(R.id.repository_txv_owner)


        mImvPicture = view.findViewById(R.id.repository_imv_picture)
        mPgbPicture = view.findViewById(R.id.repository_pgb_picture)
        mPgbPicture?.visibility = View.GONE

        view.setOnClickListener {
            userListListener.onClickRepository(mRepository!!)
        }
    }

    fun bind(repository: Repository) {
        mRepository = repository

        mTxvName!!.text = repository.name

        if (TextUtils.isEmpty(repository.description)) {
            mTxvDescription!!.visibility = View.GONE
        } else {
            mTxvDescription!!.visibility = View.VISIBLE
            mTxvDescription!!.text = repository.description
        }

        mTxvOwner!!.text = mView?.context!!.getString(R.string.repositorylist_item_owner, repository.owner?.login)

        mPgbPicture?.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get().load(repository.owner?.avatarUrl).into(mImvPicture, object: Callback {
                override fun onSuccess() {
                    mPgbPicture?.visibility = View.GONE
                }

                override fun onError(e: Exception) {
                    mPgbPicture?.visibility = View.GONE
                }
            })
        }
    }
}