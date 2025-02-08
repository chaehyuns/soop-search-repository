package com.soop.repository.data.dto.repositoryitems

import com.soop.repository.data.dto.owner.RepositoryOwnerResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryItemResponse(
    @SerialName("id")val id: Long,
    @SerialName("name")val repositoryName: String,
    @SerialName("description")val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("language")val language: String?,
    @SerialName("owner")val owner: RepositoryOwnerResponse
)
