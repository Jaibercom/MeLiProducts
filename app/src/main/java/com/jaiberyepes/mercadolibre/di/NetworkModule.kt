package com.jaiberyepes.mercadolibre.di

import com.jaiberyepes.mercadolibre.data.remote.MeLiApiService
import com.jaiberyepes.mercadolibre.data.remote.RemoteSettings.API_BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * [Module] to provide network level dependencies.
 *
 * @author jaiber.yepes
 */
@Module(includes = [CoreNetworkModule::class])
object NetworkModule {

    /**
     * Api.
     * @Provides annotated dependencies
     */
    @Provides
    @JvmStatic
    internal fun providesOkHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient = builder
        .build()

    @Provides
    @Singleton
    @JvmStatic
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()

    @Provides
    @JvmStatic
    internal fun providesBreakingBadApi(retrofit: Retrofit): MeLiApiService =
        retrofit.create(MeLiApiService::class.java)

}
