package com.jaiberyepes.mercadolibre.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Extension functions for LiveData class.
 *
 * @author jaiber.yepes
 */

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) =
    liveData.observe(this, Observer { it?.let { t -> body(t) } })

