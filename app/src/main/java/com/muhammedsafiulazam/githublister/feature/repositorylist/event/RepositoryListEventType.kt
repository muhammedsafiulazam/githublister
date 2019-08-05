package com.muhammedsafiulazam.githublister.feature.repositorylist.event

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

object RepositoryListEventType {
    // Repository list event types.
    const val LOAD_DATA_REQUEST: String = "REPOSITORYLIST_EVENT_TYPE_LOAD_DATA_REQUEST"
    const val LOAD_DATA_BUSY: String = "REPOSITORYLIST_EVENT_TYPE_LOAD_DATA_BUSY"
    const val LOAD_DATA_ERROR: String = "REPOSITORYLIST_EVENT_TYPE_LOAD_DATA_ERROR"
    const val LOAD_DATA_RESPONSE: String = "REPOSITORYLIST_EVENT_TYPE_LOAD_DATA_RESPONSE"
}