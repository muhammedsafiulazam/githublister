package com.muhammedsafiulazam.githublister.feature.repositoryinfo

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class ContributorViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private var mView: View? = null
    private var mContributor: Contributor? = null
    private var mTxvName: AppCompatTextView? = null
    private var mTxvContributions: AppCompatTextView? = null
    private var mImvPicture: AppCompatImageView? = null
    private var mPgbPicture: ProgressBar? = null

    init {
        mView = view
        mTxvName = view.findViewById(R.id.contributor_txv_name)
        mTxvContributions = view.findViewById(R.id.contributor_txv_contributions)

        mImvPicture = view.findViewById(R.id.contributor_imv_picture)
        mPgbPicture = view.findViewById(R.id.contributor_pgb_picture)
        mPgbPicture?.visibility = View.GONE

    }

    fun bind(contributor: Contributor) {
        mContributor = contributor

        mTxvName!!.text = contributor.login
        mTxvContributions!!.text = mView?.context!!.getString(R.string.repositoryinfo_countribution_count, contributor.contributions)

        mPgbPicture?.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get().load(contributor.avatarUrl).into(mImvPicture, object: Callback {
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