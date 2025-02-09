package com.soop.repository

import com.soop.repository.domain.model.RepositoryDetail
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.model.UserProfile

const val CACHE_SIZE_10MB = 10L * 1024 * 1024
const val CACHE_SIZE_5KB = 5L * 1024
const val CACHED_1_MINUTES = 60
const val CACHED_1_SECOND = 1
const val EXPECTED_BODY = "chaehyun & soop"
const val TEST_ERROR_MESSAGE = "Test Error Message"

val repositoryItem1 = RepositoryItem(
    id = 1,
    repositoryName = "soop",
    description = "soop repository",
    stargazersCount = 100,
    language = "Kotlin",
    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    ownerLoginName = "chaehyun"
)

val repositoryItem2 = RepositoryItem(
    id = 2,
    repositoryName = "soop2",
    description = "soop repository2",
    stargazersCount = 5000,
    language = "Kotlin",
    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    ownerLoginName = "chaehyun"
)

val repositoryDetail1 = RepositoryDetail(
    id = 1,
    repositoryName = "soop",
    repositoryUrl = "https://github.com",
    description = "soop repository",
    stargazersCount = 100,
    watchersCount = 30,
    forksCount = 50,
    ownerAvatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    ownerLoginName = "chaehyun"
)

val userProfile1 = UserProfile(
    id = 1,
    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    loginName = "chaehyun",
    publicRepos = 10,
    followers = 20,
    following = 30,
    bio = "soop",
    languages = setOf("Kotlin", "Java")
)
