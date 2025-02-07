package com.soop.repository.presentation.main.model

import com.soop.repository.domain.model.RepositoryItem

data class RepositoryItemUiModel(
    val id: Long,
    val repositoryName: String,
    val description: String,
    val starCountText: String,
    val language: String,
    val ownerAvatarUrl: String,
    val ownerLoginName: String
) {
    companion object {
        fun RepositoryItem.toUiModel(): RepositoryItemUiModel {
            return RepositoryItemUiModel(
                id = id,
                repositoryName = repositoryName,
                description = description ?: "-",
                starCountText = stargazersCount.toString(),
                language = language ?: "-",
                ownerAvatarUrl = ownerAvatarUrl,
                ownerLoginName = ownerLoginName
            )
        }
    }
}
