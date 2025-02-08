package com.soop.repository.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.profile.model.ProfileUiModel.Companion.toUiModel
import com.soop.repository.presentation.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun fetchUserProfile(username: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val profile = repository.getUserProfile(username)
                _uiState.value = ProfileUiState.Success(profile.toUiModel())
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message)
            }
        }
    }
}
