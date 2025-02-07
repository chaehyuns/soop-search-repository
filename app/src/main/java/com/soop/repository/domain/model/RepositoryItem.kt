package com.soop.repository.domain.model

data class RepositoryItem(
    val id: Long,
    val repositoryName: String,
    val description: String?,
    val stargazersCount: Int,
    val language: String?,
    val ownerAvatarUrl: String,
    val ownerLoginName: String
)
