package com.soop.repository.presentation.main.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed class RepositoryUiState {
    data object Initial : RepositoryUiState()
    data object Loading : RepositoryUiState()
    data class Success(val data: Flow<PagingData<RepositoryItemUiModel>>) : RepositoryUiState()
    data class Error(val message: String?) : RepositoryUiState()
}
