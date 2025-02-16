package com.soop.repository.presentation.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.soop.repository.presentation.main.MainScreenRoot
import com.soop.repository.presentation.profile.ProfileScreen
import com.soop.repository.presentation.repositorydetail.RepositoryDetailScreen
import com.soop.repository.presentation.ui.theme.AppWhite

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RepositoryNavGraph() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetBackgroundColor = AppWhite()
    ) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.REPOSITORY_LIST
        ) {
            composable(NavRoutes.REPOSITORY_LIST) {
                MainScreenRoot(
                    onRepositoryClick = { owner, repo ->
                        navController.navigate("${NavRoutes.REPOSITORY_DETAIL}/$owner/$repo")
                    }
                )
            }
            composable(
                route = "${NavRoutes.REPOSITORY_DETAIL}/{${NavArgs.OWNER}}/{${NavArgs.REPO}}",
                arguments = listOf(
                    navArgument(NavArgs.OWNER) { type = NavType.StringType },
                    navArgument(NavArgs.REPO) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val owner = backStackEntry.arguments?.getString(NavArgs.OWNER) ?: ""
                val repo = backStackEntry.arguments?.getString(NavArgs.REPO) ?: ""
                RepositoryDetailScreen(
                    owner = owner,
                    repo = repo,
                    onMoreClick = {
                        navController.navigate("${NavRoutes.PROFILE_BOTTOM_SHEET}/$owner")
                    }
                )
            }
            bottomSheet(
                route = "${NavRoutes.PROFILE_BOTTOM_SHEET}/{${NavArgs.USERNAME}}",
                arguments = listOf(
                    navArgument(NavArgs.USERNAME) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString(NavArgs.USERNAME) ?: ""
                ProfileScreen(username = username)
            }
        }
    }
}
