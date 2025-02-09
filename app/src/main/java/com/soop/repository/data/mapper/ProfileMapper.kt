package com.soop.repository.data.mapper

import com.soop.repository.data.dto.profile.GithubUserResponse
import com.soop.repository.domain.model.UserProfile

fun GithubUserResponse.toDomain(languages: Set<String>): UserProfile {
    return UserProfile(
        id = this.id,
        avatarUrl = this.avatarUrl,
        loginName = this.login,
        publicRepos = this.publicRepos,
        followers = this.followers,
        following = this.following,
        bio = this.bio,
        languages = languages
    )
}
