package com.muhammedsafiulazam.githublister.addon

import android.text.TextUtils

open class AddOn {
    // List of addons.
    private val mAddOns: MutableMap<String, IAddOn> = mutableMapOf()

    /**
     * Get addon.
     * @param type type of addon
     * @return addon for type
     */
    fun getAddOn(type: String) : IAddOn? {
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
    fun getAddOns() : Map<String, IAddOn> {
        return mAddOns.toMap()
    }

    /**
     * Add addon.
     * @param type type of addon
     * @param addOn addon to be added
     */
    fun addAddOn(type: String, addOn: IAddOn) {
        mAddOns.put(type, addOn)
    }

    /**
     * Add addons.
     * @param addons map of addons
     */
    fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOns.putAll(addons)
    }

    /**
     * Remove addon.
     * @param type type of addon
     */
    fun removeAddOn(type: String) {
        mAddOns.remove(type)
    }

    /**
     * Remove addons.
     * @param types types of addons
     */
    fun removeAddOns(types: List<String>) {
        types.forEach { key ->
            mAddOns.remove(key)
        }
    }

    /**
     * Clear addons.
     */
    open fun clearAddOns() {
        // Clear.
        mAddOns.clear()
    }
}