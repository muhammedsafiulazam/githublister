package com.muhammedsafiulazam.githublister

import android.app.Application
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

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {

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

    companion object {
        private lateinit var instance: MainApplication

        /**
         * Singleton instance.
         * @return single instance
         */
        fun getInstance() : MainApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    /**
     * Get activity manager.
     * @return activity manager
     */
    fun getActivityManager() : IActivityManager {
        return mActivityManager
    }

    /**
     * Get server manager.
     * @return server manager
     */
    fun getServerManager() : IServerManager {
        return mServerManager
    }

    /**
     * Get service manager.
     * @return service manager
     */
    fun getServiceManager() : IServiceManager {
        return mServiceManager
    }

    /**
     * Get event manager.
     * @return event manager
     */
    fun getEventManager() : IEventManager {
        return mEventManager
    }

    /**
     * Get retrofit manager.
     * @return retrofit manager
     */
    fun getRetrofitManager() : IRetrofitManager {
        return mRetrofitManger
    }

    /**
     * Get queue manager.
     * @return queue manager
     */
    fun getQueueManager() : IQueueManager {
        return mQueueManager
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}