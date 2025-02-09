package com.soop.repository.domain.repository

import androidx.paging.PagingData
import com.soop.repository.domain.model.RepositoryDetail
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>>

    suspend fun getRepositoryDetail(owner: String, repo: String): RepositoryDetail

    suspend fun getUserProfile(username: String): UserProfile
}
