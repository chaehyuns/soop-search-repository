package com.soop.repository.data.network

suspend fun <T> safeApiCall(
    apiCall: suspend () -> retrofit2.Response<T>,
    errorMessage: String
): T {
    try {
        val response = apiCall()
        if (!response.isSuccessful) {
            throw GithubApiException("$errorMessage: ${response.code()}, ${response.message()}")
        }
        return response.body() ?: throw GithubApiException("$errorMessage: Empty body")
    } catch (e: Exception) {
        throw GithubApiException("Failed to $errorMessage", e)
    }
}
