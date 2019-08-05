package com.muhammedsafiulazam.githublister.feature.repositorylist

import com.muhammedsafiulazam.githublister.network.model.repository.Repository

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IRepositoryListListener {
    fun onClickRepository(repository: Repository)
}