package com.muhammedsafiulazam.githublister.activity

import androidx.lifecycle.ViewModel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivityModel : ViewModel() {

    private var mActivity: BaseActivity? = null

    fun getActivity() : BaseActivity? {
        return mActivity
    }

    fun setActivity(activity: BaseActivity?) {
        mActivity = activity
    }

    open fun onCreateActivity() {
    }

    open fun onStartActivity() {
    }

    open fun onStopActivity() {
    }

    open fun onDestroyActivity() {
    }
}