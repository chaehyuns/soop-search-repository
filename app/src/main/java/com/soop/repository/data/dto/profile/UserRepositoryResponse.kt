package com.soop.repository.data.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRepositoryResponse(
    @SerialName("language") val language: String?
)
