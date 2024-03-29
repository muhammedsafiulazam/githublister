package com.muhammedsafiulazam.githublister.activity

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.muhammedsafiulazam.githublister.addon.AddOnManager
import com.muhammedsafiulazam.githublister.addon.AddOnType
import com.muhammedsafiulazam.githublister.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivityModel : ViewModel(), IAddOn {

    private var mActivity: BaseActivity? = null
    private val mAddOns: MutableMap<String, IAddOn> = mutableMapOf()

    fun getActivity() : BaseActivity? {
        return mActivity
    }

    fun setActivity(activity: BaseActivity?) {
        mActivity = activity
    }

    open fun onCreateActivity() {
        // Essential addons for activity model.
        addAddOn(AddOnType.SERVICE_MANAGER, AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
    }

    open fun onStartActivity() {
    }

    open fun onResumeActivity() {

    }

    open fun onPauseActivity() {

    }

    open fun onStopActivity() {
    }

    open fun onDestroyActivity() {
        clearAddOns()
    }

    // Addons related methods.

    /**
     * Get addon.
     * @param type type of addon
     * @return addon for type
     */
    override fun getAddOn(type: String) : IAddOn? {
        return mAddOns.get(type)
    }

    /**
     * Get addons.
     * @return map of addons
     */
    override fun getAddOns() : Map<String, IAddOn> {
        return mAddOns
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