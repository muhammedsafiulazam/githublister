package com.muhammedsafiulazam.githublister.feature.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedsafiulazam.githublister.R
import com.muhammedsafiulazam.githublister.network.model.repository.Repository

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class RepositoryListAdapter(val repositoryList: MutableList<Repository>, val repositoryListListener: IRepositoryListListener) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_repository_item, parent, false),
            repositoryListListener
        )
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository: Repository = repositoryList.get(position);
        holder.bind(repository)
    }
}