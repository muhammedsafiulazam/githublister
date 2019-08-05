package com.muhammedsafiulazam.githublister.activity

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.muhammedsafiulazam.githublister.MainApplication

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivity : AppCompatActivity() {

    companion object {
        const val KEY_DATA: String = "KEY_DATA"
    }

    private var mActivityModel: BaseActivityModel? = null

    private var isViewReady: Boolean = false

    fun getData() : Parcelable? {
        return intent?.getParcelableExtra(KEY_DATA)
    }

    fun getActivityModel() : BaseActivityModel? {
        return mActivityModel
    }

    fun setActivityModel(activityModel: Class<out BaseActivityModel>) {
        mActivityModel = ViewModelProviders.of(this).get(activityModel)
        mActivityModel?.setActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isViewReady = false
    }

    override fun onStart() {
        super.onStart()

        if (!isViewReady) {
            isViewReady = true
            mActivityModel?.onCreateActivity()
        }

        mActivityModel?.onStartActivity()
        MainApplication.getInstance().getActivityManager().onStartActivity(this)
    }

    override fun onStop() {
        super.onStop()
        mActivityModel?.onStopActivity()
        MainApplication.getInstance().getActivityManager().onStopActivity(this)
    }

    override fun onDestroy() {
        mActivityModel?.onDestroyActivity()
        super.onDestroy()
    }
}