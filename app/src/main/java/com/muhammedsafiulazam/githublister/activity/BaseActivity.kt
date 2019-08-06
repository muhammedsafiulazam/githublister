package com.muhammedsafiulazam.githublister.activity

import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.muhammedsafiulazam.githublister.addon.AddOnManager
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivity : AppCompatActivity(), IAddOn {
    companion object {
        const val KEY_DATA: String = "KEY_DATA"
    }

    private var mActivityModel: BaseActivityModel? = null
    private var mActivityManager: IActivityManager? = null
    private val mAddOns: MutableMap<String, IAddOn> = mutableMapOf()

    private var isViewReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Essential addons for activity.
        addAddOn(AddOnType.ACTIVITY_MANAGER, AddOnManager.getAddOn(AddOnType.ACTIVITY_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)

        isViewReady = false
    }

    override fun onStart() {
        super.onStart()

        if (!isViewReady) {
            isViewReady = true
            mActivityModel?.onCreateActivity()
        }

        mActivityModel?.onStartActivity()
        getActivityManager()?.onStartActivity(this)
    }

    override fun onStop() {
        super.onStop()
        mActivityModel?.onStopActivity()
        getActivityManager()?.onStopActivity(this)
    }

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

    private fun getActivityManager() : IActivityManager? {
        if (mActivityManager == null) {
            mActivityManager = AddOnManager.getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager
        }
        return mActivityManager
    }

    override fun onDestroy() {
        clearAddOns()
        mActivityModel?.onDestroyActivity()
        super.onDestroy()
    }

    // Addons related methods.

    /**
     * Get addon.
     * @param type type of addon
     * @return addon for type
     */
    override fun getAddOn(type: String) : IAddOn? {
        var addOn: IAddOn? = null
        mAddOns.forEach { key, value ->
            if (TextUtils.equals(key, type)) {
                addOn = value
                return@forEach
            }
        }
        return addOn
    }

    /**
     * Get addons.
     * @return map of addons
     */
    override fun getAddOns() : Map<String, IAddOn> {
        return mAddOns.toMap()
    }

    /**
     * Add addon.
     * @param type type of addon
     * @param addOn addon to be added
     */
    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOns.put(type, addOn)
    }

    /**
     * Add addons.
     * @param addons map of addons
     */
    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOns.putAll(addons)
    }

    /**
     * Remove addon.
     * @param type type of addon
     */
    override fun removeAddOn(type: String) {
        mAddOns.remove(type)
    }

    /**
     * Remove addons.
     * @param types types of addons
     */
    override fun removeAddOns(types: List<String>) {
        types.forEach { key ->
            mAddOns.remove(key)
        }
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        // Clear.
        mAddOns.clear()
    }
}