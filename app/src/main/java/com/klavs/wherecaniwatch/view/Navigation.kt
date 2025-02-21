package com.klavs.wherecaniwatch.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.klavs.wherecaniwatch.data.entities.Routes
import com.klavs.wherecaniwatch.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        //bottomBar = { BottomBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { //innerPadding ->
        NavHost(
            //modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            startDestination = Routes.Home,
            navController = navController
        ) {
            composable<Routes.Home> {
                val viewModel = koinViewModel<HomeViewModel>()
                Home(viewModel)
            }
        }

    }
}

/*@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val route = destination.parent?.route?: return koinViewModel()
    val backStackEntry = remember(this) {
        navController.getBackStackEntry(route)
    }
    return koinViewModel(viewModelStoreOwner = backStackEntry)
}*/

/*@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun BottomBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val items = BottomBarItem.items.value
    FlexibleBottomAppBar {
        items.forEach { item ->
            val selected =
                currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) }
            )
        }
    }
}*/

@Preview
@Composable
private fun NavigationPreview() {
    Navigation()
}