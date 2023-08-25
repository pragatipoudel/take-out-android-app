package com.example.takeout.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.takeout.R
import com.example.takeout.ui.Screen
import com.example.takeout.ui.data.DataSource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val screenTitle = stringResource(Screen.valueOf(currentBackStackEntry?.destination?.route ?: Screen.Home.name).resourceId)


    Scaffold(
        topBar = {
            MyNewTopAppBar(
                title = if (screenTitle == "Home") stringResource(id = R.string.app_name) else screenTitle,
                canBack = navController.previousBackStackEntry != null,
                goBack = { navController.navigateUp() })
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Home.name) {
                HomeScreen(navController, modifier=Modifier.fillMaxSize())
            }
            composable(Screen.MonthView.name) {
                MonthViewScreen()
            }
            composable(Screen.TrendView.name) {
                TrendViewScreen()
            }
            composable(Screen.Settings.name) {
                SettingsScreen()
            }
            composable(Screen.AddNewEntry.name) {
                AddNewEntryScreen()
            }
            composable(Screen.FoodList.name) {
                FoodListScreen()
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNewTopAppBar(
    title: String,
    canBack: Boolean,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = Modifier
            .background(Color.Cyan),
        navigationIcon = { if (canBack) {
            IconButton(onClick = { goBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        } }
    )
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.MonthView,
        Screen.TrendView,
        Screen.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach {screen ->
            val label = stringResource(id = screen.resourceId)
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = screen.icon ?: R.drawable.food), contentDescription = null) },
                label = { Text(text = label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.name} == true,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }

    }


}

@Composable
fun CurrentMonthTotalBar() {

    Text(text = "June Total: $1000")
    
}