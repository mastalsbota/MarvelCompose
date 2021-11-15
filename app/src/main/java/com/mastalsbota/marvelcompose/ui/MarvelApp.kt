package com.mastalsbota.marvelcompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mastalsbota.marvelcompose.R
import com.mastalsbota.marvelcompose.ui.navigation.AppBottomNavigation
import com.mastalsbota.marvelcompose.ui.navigation.DrawerContent
import com.mastalsbota.marvelcompose.ui.navigation.NavItem
import com.mastalsbota.marvelcompose.ui.navigation.Navigation
import com.mastalsbota.marvelcompose.ui.navigation.navigatePoppingUpToStartDestination
import com.mastalsbota.marvelcompose.ui.screens.common.AppBarIcon
import com.mastalsbota.marvelcompose.ui.theme.MarvelComposeTheme
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""
    val showUpNavigation = currentRoute !in NavItem.values().map { it.navCommand.route }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerOptions = listOf(NavItem.HOME, NavItem.SETTINGS)
    val bottomNavOptions = listOf(NavItem.CHARACTERS, NavItem.COMICS, NavItem.EVENTS)

    val showBottomNavigation =
        bottomNavOptions.any { currentRoute.contains(it.navCommand.feature.route) }
    val drawerSelectedIndex = if (showBottomNavigation) {
        drawerOptions.indexOf(NavItem.HOME)
    } else {
        drawerOptions.indexOfFirst { it.navCommand.route == currentRoute }
    }

    MarvelScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        if (showUpNavigation) {
                            AppBarIcon(
                                imageVector = Icons.Default.ArrowBack,
                                onClick = { navController.popBackStack() })
                        } else {
                            AppBarIcon(
                                imageVector = Icons.Default.Menu,
                                onClick = { scope.launch { scaffoldState.drawerState.open() } }
                            )
                        }
                    }
                )
            },
            bottomBar = {
                if (showBottomNavigation) {
                    AppBottomNavigation(
                        bottomNavOptions = bottomNavOptions,
                        currentRoute = currentRoute
                    ) {
                        navController.navigatePoppingUpToStartDestination(it.navCommand.route)
                    }
                }
            },
            drawerContent = {
                DrawerContent(
                    drawerOptions = drawerOptions,
                    selectedIndex = drawerSelectedIndex,
                    onOptionClick = { navItem ->
                        scope.launch { scaffoldState.drawerState.close() }
                        navController.navigate(navItem.navCommand.route)
                    }
                )
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController)
            }
        }
    }
}

@Composable
fun MarvelScreen(content: @Composable () -> Unit) {
    MarvelComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}