package com.soop.repository.data.dto.owner

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryOwnerResponse(
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("login") val loginName: String
)
