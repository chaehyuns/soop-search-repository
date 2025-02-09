package com.soop.repository.repository

import androidx.paging.PagingData
import com.soop.repository.data.network.GithubApiException
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ErrorGithubRepository(private val errorMessage: String) : GithubRepository {
    override fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>> {
        return flow {
            throw GithubApiException(errorMessage)
        }
    }

    override suspend fun getRepositoryDetail(owner: String, repo: String) =
        throw RuntimeException(errorMessage)

    override suspend fun getUserProfile(username: String) = throw RuntimeException(errorMessage)
}
