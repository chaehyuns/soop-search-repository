package com.soop.repository.data.mapper

import com.soop.repository.data.dto.repositorydetail.RepositoryDetailResponse
import com.soop.repository.domain.model.RepositoryDetail

fun RepositoryDetailResponse.toDomain(): RepositoryDetail {
    return RepositoryDetail(
        id = id,
        repositoryName = name,
        repositoryUrl = htmlUrl,
        description = description,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        ownerAvatarUrl = owner.avatarUrl,
        ownerLoginName = owner.loginName
    )
}
