package com.muhammedsafiulazam.githublister.addon

import android.content.Context
import android.content.res.Resources
import com.muhammedsafiulazam.githublister.activity.ActivityManager
import com.muhammedsafiulazam.githublister.activity.IActivityManager
import com.muhammedsafiulazam.githublister.event.EventManager
import com.muhammedsafiulazam.githublister.event.IEventManager
import com.muhammedsafiulazam.githublister.network.queue.IQueueManager
import com.muhammedsafiulazam.githublister.network.queue.QueueManager
import com.muhammedsafiulazam.githublister.network.retrofit.IRetrofitManager
import com.muhammedsafiulazam.githublister.network.retrofit.RetrofitManager
import com.muhammedsafiulazam.githublister.network.server.IServerManager
import com.muhammedsafiulazam.githublister.network.server.ServerManager
import com.muhammedsafiulazam.githublister.network.service.IServiceManager
import com.muhammedsafiulazam.githublister.network.service.ServiceManager

object AddOnManager : AddOn(), IAddOn {

    // Context.
    private var mContext: Context? = null

    // Activity manager.
    private val mActivityManager: IActivityManager by lazy {
        ActivityManager()
    }

    // Server manager.
    private val mServerManager: IServerManager by lazy {
        ServerManager()
    }

    // Service manager.
    private val mServiceManager: IServiceManager by lazy {
        ServiceManager()
    }

    // Event manager.
    private val mEventManager: IEventManager by lazy {
        EventManager()
    }

    // Retrofit manager.
    private val mRetrofitManger: IRetrofitManager by lazy {
        RetrofitManager()
    }

    // Queue manager.
    private val mQueueManager: IQueueManager by lazy {
        QueueManager()
    }

    /**
     * Initialize with context.
     * @param context context
     */
    fun initialize(context: Context) {
        mContext = context
        onInitialize()
    }

    private fun onInitialize() {
        // Activity manager.
        addAddOn(AddOnType.ACTIVITY_MANAGER, mActivityManager)

        // Server manager.
        addAddOn(AddOnType.SERVER_MANAGER, mServerManager)

        // Service manager.
        addAddOn(AddOnType.SERVICE_MANAGER, mServiceManager)

        // Event manager.
        addAddOn(AddOnType.EVENT_MANAGER, mEventManager)

        // Retrofit manager.
        addAddOn(AddOnType.RETROFIT_MANAGER, mRetrofitManger)

        // Queue manager.
        addAddOn(AddOnType.QUEUE_MANAGER, mQueueManager)

        // Now assign individually.

        // Service manager.
        mServiceManager.addAddOn(AddOnType.SERVER_MANAGER, mServerManager)
        mServiceManager.addAddOn(AddOnType.EVENT_MANAGER, mEventManager)
        mServiceManager.addAddOn(AddOnType.QUEUE_MANAGER, mQueueManager)

        // Server manager.
        mServerManager.addAddOn(AddOnType.RETROFIT_MANAGER, mRetrofitManger)
    }

    /**
     * Get context.
     * @return context
     */
    fun getContext() : Context {
        return mContext!!
    }

    /**
     * Get resources.
     * @return resources
     */
    fun getResources() : Resources {
        return mContext!!.resources
    }
}