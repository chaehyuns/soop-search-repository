package com.soop.repository.presentation.profile.model

import com.soop.repository.domain.model.UserProfile
import com.soop.repository.presentation.util.formatCount

data class ProfileUiModel(
    val id: Int,
    val avatarUrl: String,
    val loginName: String,
    val publicRepos: String,
    val followers: String,
    val following: String,
    val bio: String,
    val languages: String
) {
    companion object {
        fun UserProfile.toUiModel(): ProfileUiModel {
            return ProfileUiModel(
                id = this.id,
                avatarUrl = this.avatarUrl,
                loginName = this.loginName,
                publicRepos = this.publicRepos.toString(),
                followers = this.followers.formatCount(),
                following = this.following.formatCount(),
                bio = this.bio ?: "",
                languages = this.languages.joinToString(", ")
            )
        }
    }
}
