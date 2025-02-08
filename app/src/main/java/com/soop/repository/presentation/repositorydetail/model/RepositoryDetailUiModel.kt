package com.soop.repository.presentation.repositorydetail.model

import com.soop.repository.domain.model.RepositoryDetail
import com.soop.repository.presentation.util.formatCount

data class RepositoryDetailUiModel(
    val id: Long,
    val repositoryName: String,
    val repositoryUrl: String,
    val stars: String,
    val watchers: String,
    val forks: String,
    val description: String,
    val ownerAvatarUrl: String,
    val ownerLoginName: String
) {
    companion object {
        fun RepositoryDetail.toUiModel() = RepositoryDetailUiModel(
            id = id,
            repositoryName = repositoryName,
            repositoryUrl = repositoryUrl,
            stars = stargazersCount.formatCount(),
            watchers = watchersCount.formatCount(),
            forks = forksCount.formatCount(),
            description = description,
            ownerAvatarUrl = ownerAvatarUrl,
            ownerLoginName = ownerLoginName
        )
    }
}
