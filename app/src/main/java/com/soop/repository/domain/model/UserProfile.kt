package com.soop.repository.domain.model

data class UserProfile(
    val id: Int,
    val avatarUrl: String,
    val loginName: String,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val bio: String?,
    val languages: Set<String>
)
