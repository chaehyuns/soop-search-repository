package com.soop.repository.data.network

class GithubApiException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
