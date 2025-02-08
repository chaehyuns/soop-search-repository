package com.soop.repository.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.soop.repository.data.dto.profile.GithubUserResponse
import com.soop.repository.data.mapper.toDomain
import com.soop.repository.data.network.GithubApiException
import com.soop.repository.data.network.GithubApiService
import com.soop.repository.data.network.safeApiCall
import com.soop.repository.data.paging.RepositoryPagingSource
import com.soop.repository.domain.model.RepositoryDetail
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.model.UserProfile
import com.soop.repository.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGithubRepository @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {
    override fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { RepositoryPagingSource(apiService, query) }
        ).flow
    }

    override suspend fun getRepositoryDetail(owner: String, repo: String): RepositoryDetail {
        try {
            val repoDto = safeApiCall(
                apiCall = { apiService.getRepositoryDetail(owner, repo) },
                errorMessage = "fetching repository detail"
            )
            return repoDto.toDomain()
        } catch (e: Exception) {
            throw GithubApiException("Failed to fetch repository detail", e)
        }
    }

    override suspend fun getUserProfile(username: String): UserProfile {
        try {
            val userDto = fetchUserDto(username)
            val languages = fetchUserLanguages(username)
            return userDto.toDomain(languages)
        } catch (e: Exception) {
            throw GithubApiException("Failed to fetch user profile", e)
        }
    }

    private suspend fun fetchUserDto(username: String): GithubUserResponse =
        safeApiCall(
            apiCall = { apiService.getUser(username) },
            errorMessage = "fetching user"
        )

    private suspend fun fetchUserLanguages(username: String): Set<String> {
        val reposDto = safeApiCall(
            apiCall = { apiService.getRepositories(username) },
            errorMessage = "fetching repositories"
        )
        return reposDto.mapNotNull { it.language }.toSet()
    }
}
