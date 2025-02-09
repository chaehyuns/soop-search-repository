package com.soop.repository.domain.model

data class RepositoryDetail(
    val id: Long,
    val repositoryName: String,
    val repositoryUrl: String,
    val description: String?,
    val stargazersCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val ownerAvatarUrl: String,
    val ownerLoginName: String
)
