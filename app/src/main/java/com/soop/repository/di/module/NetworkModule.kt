package com.soop.repository.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.soop.repository.data.network.AuthorizationInterceptor
import com.soop.repository.data.network.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.github.com/"
    private const val DEFAULT_CACHE_SIZE = 10L * 1024 * 1024
    private const val CACHED_24_HOURS = 60 * 60 * 24
    private const val CACHED_5_MINUTES = 60 * 5
    private const val HEADER_CACHE_CONTROL = "Cache-Control"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, DEFAULT_CACHE_SIZE)

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor = AuthorizationInterceptor()

    @Provides
    @Singleton
    @Named("network_cache")
    fun provideNetworkCacheInterceptor(): Interceptor =
        Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = response.header(HEADER_CACHE_CONTROL)
            if (cacheControl.isNullOrEmpty() ||
                cacheControl.contains("no-store") ||
                cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") ||
                cacheControl.contains("max-age=0")
            ) {
                response.newBuilder()
                    .header(HEADER_CACHE_CONTROL, "public, max-age=$CACHED_5_MINUTES")
                    .build()
            } else {
                response
            }
        }

    @Provides
    @Singleton
    @Named("offline_cache")
    fun provideOfflineCacheInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            var request = chain.request()
            if (!isNetworkAvailable(context)) {
                request = request.newBuilder()
                    .header(HEADER_CACHE_CONTROL, "only-if-cached, max-stale=$CACHED_24_HOURS")
                    .build()
            }
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cache: Cache,
        authorizationInterceptor: AuthorizationInterceptor,
        @Named("offline_cache") offlineCacheInterceptor: Interceptor,
        @Named("network_cache") networkCacheInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .addNetworkInterceptor(networkCacheInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
