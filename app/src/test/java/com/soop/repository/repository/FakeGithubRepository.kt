package com.soop.repository.repository

import androidx.paging.PagingData
import com.soop.repository.domain.model.RepositoryDetail
import com.soop.repository.domain.model.RepositoryItem
import com.soop.repository.domain.model.UserProfile
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.repositoryDetail1
import com.soop.repository.repositoryItem1
import com.soop.repository.repositoryItem2
import com.soop.repository.userProfile1
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGithubRepository : GithubRepository {
    private val fakeRepositoryList = listOf(
        repositoryItem1,
        repositoryItem2
    )

    override fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>> {
        return flow {
            emit(PagingData.from(fakeRepositoryList))
        }
    }

    override suspend fun getRepositoryDetail(owner: String, repo: String): RepositoryDetail {
        return repositoryDetail1
    }

    override suspend fun getUserProfile(username: String): UserProfile {
        return userProfile1
    }
}
