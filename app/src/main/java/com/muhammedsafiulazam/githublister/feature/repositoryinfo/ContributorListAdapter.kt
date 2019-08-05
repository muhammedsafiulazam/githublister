package com.muhammedsafiulazam.githublister.feature.repositoryinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.network.model.contributor.Contributor

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

class ContributorListAdapter(val contributorList: MutableList<Contributor>) : RecyclerView.Adapter<ContributorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_contributor_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return contributorList.size
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val contributor: Contributor = contributorList.get(position);
        holder.bind(contributor)
    }
}