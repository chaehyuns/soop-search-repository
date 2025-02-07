package com.soop.repository.domain.repository

import androidx.paging.PagingData
import com.soop.repository.domain.model.RepositoryItem
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun searchRepositories(query: String): Flow<PagingData<RepositoryItem>>
}
