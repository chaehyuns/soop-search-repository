package com.soop.repository.data.network

import com.soop.repository.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$HEADER_BEARER_PREFIX${BuildConfig.GITHUB_API_KEY}")
            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_VALUE)
            .addHeader(HEADER_GITHUB_API_VERSION, HEADER_GITHUB_API_VERSION_VALUE)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_BEARER_PREFIX = "Bearer "
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_ACCEPT_VALUE = "application/vnd.github+json"
        private const val HEADER_GITHUB_API_VERSION = "X-GitHub-Api-Version"
        private const val HEADER_GITHUB_API_VERSION_VALUE = "2022-11-28"
    }
}
