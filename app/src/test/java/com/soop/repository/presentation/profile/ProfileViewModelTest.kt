package com.soop.repository.presentation.profile

import com.soop.repository.TEST_ERROR_MESSAGE
import com.soop.repository.domain.repository.GithubRepository
import com.soop.repository.presentation.profile.model.ProfileUiState
import com.soop.repository.repository.ErrorGithubRepository
import com.soop.repository.repository.FakeGithubRepository
import com.soop.repository.userProfile1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileViewModelTest {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var fakeRepository: GithubRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeGithubRepository()
        viewModel = ProfileViewModel(fakeRepository)
    }

    @Test
    fun `초기 상태는 Loading이어야 한다`() = runTest {
        val initialState = viewModel.uiState.first()
        assertThat(initialState).isInstanceOf(ProfileUiState.Loading::class.java)
    }

    @Test
    fun `fetchUserProfile 정상 호출 시 Success 상태로 변경되어야 한다`() = runTest {
        // When
        viewModel.fetchUserProfile(username = "chaehyun")
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(ProfileUiState.Success::class.java)
    }

    @Test
    fun `fetchUserProfile 정상 호출 시 올바른 데이터를 불러온다`() = runTest {
        // When
        viewModel.fetchUserProfile(
            username = userProfile1.loginName
        )
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        val data = (result as? ProfileUiState.Success)?.data
        assertThat(data?.loginName).isEqualTo(userProfile1.loginName)
        assertThat(data?.avatarUrl).isEqualTo(userProfile1.avatarUrl)
    }

    @Test
    fun `API 호출 실패 시 Error 상태로 변경되어야 한다`() = runTest {
        // Given
        val errorRepository = ErrorGithubRepository(errorMessage = TEST_ERROR_MESSAGE)
        viewModel = ProfileViewModel(errorRepository)

        // When
        viewModel.fetchUserProfile("chaehyun")
        advanceUntilIdle()

        // Then
        val result = viewModel.uiState.first()
        assertThat(result).isInstanceOf(ProfileUiState.Error::class.java)

        val errorMessage = (result as? ProfileUiState.Error)?.message ?: "Unknown error"
        assertThat(errorMessage).isEqualTo(TEST_ERROR_MESSAGE)
    }
}
