package com.soop.repository.data.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResponse(
    @SerialName("id") val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("login") val login: String,
    @SerialName("public_repos") val publicRepos: Int,
    @SerialName("followers") val followers: Int,
    @SerialName("following") val following: Int,
    @SerialName("bio") val bio: String?
)
