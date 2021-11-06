package com.jaiberyepes.mercadolibre

import androidx.lifecycle.LifecycleObserver
import com.jaiberyepes.mercadolibre.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Project main Application class that inherits from [DaggerApplication].
 *
 * @author jaiber.yepes
 */
class BaseApplication : DaggerApplication(), LifecycleObserver {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()

        // Timber
        Timber.plant(Timber.DebugTree())

    }
}
