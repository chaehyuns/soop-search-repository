package com.soop.repository.data.mapper

import com.soop.repository.data.dto.repositoryitems.RepositoryItemResponse
import com.soop.repository.domain.model.RepositoryItem

fun RepositoryItemResponse.toDomain(): RepositoryItem {
    return RepositoryItem(
        id = id,
        repositoryName = repositoryName,
        description = description,
        stargazersCount = stargazersCount,
        language = language,
        ownerAvatarUrl = owner.avatarUrl,
        ownerLoginName = owner.loginName
    )
}
