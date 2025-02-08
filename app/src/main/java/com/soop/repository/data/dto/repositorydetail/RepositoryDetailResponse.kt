package com.soop.repository.data.dto.repositorydetail

import com.soop.repository.data.dto.owner.RepositoryOwnerResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("description") val description: String,
    @SerialName("owner") val owner: RepositoryOwnerResponse
)
