package com.soop.repository.presentation.profile.model

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val data: ProfileUiModel) : ProfileUiState()
    data class Error(val message: String?) : ProfileUiState()
}
