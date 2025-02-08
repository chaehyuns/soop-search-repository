package com.soop.repository.presentation.repositorydetail.model

sealed class RepositoryDetailUiState {
    data object Loading : RepositoryDetailUiState()
    data class Success(val data: RepositoryDetailUiModel) : RepositoryDetailUiState()
    data class Error(val message: String?) : RepositoryDetailUiState()
}
