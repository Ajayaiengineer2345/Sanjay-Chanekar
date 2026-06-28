package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.state.AppTheme
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.DirectoryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val directoryViewModel: DirectoryViewModel = viewModel()
            val settingsState by directoryViewModel.settingsState.collectAsState()
            
            MyApplicationTheme(
                selectedTheme = settingsState.selectedTheme,
                isDarkMode = settingsState.isDarkMode
            ) {
                MainAppContainer(viewModel = directoryViewModel)
            }
        }
    }
}

// Routes
const val ROUTE_HOME = "home"
const val ROUTE_DIRECTORY = "directory"
const val ROUTE_SERVICES = "services"
const val ROUTE_ABOUT = "about"
const val ROUTE_SETTINGS = "settings"

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(viewModel: DirectoryViewModel) {
    val navController = rememberNavController()
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    
    val navItems = listOf(
        NavigationItem(ROUTE_HOME, Icons.Default.Home, "Home"),
        NavigationItem(ROUTE_DIRECTORY, Icons.Default.Search, "Directory"),
        NavigationItem(ROUTE_SERVICES, Icons.Default.Campaign, "Services"),
        NavigationItem(ROUTE_ABOUT, Icons.Default.Person, "About"),
        NavigationItem(ROUTE_SETTINGS, Icons.Default.Settings, "Settings")
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = theme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                navItems.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (isSelected) theme.secondary else Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                color = if (isSelected) Color.White else Color.Gray,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = theme.primary.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.testTag("nav_item_${item.route}")
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_HOME,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(ROUTE_HOME) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToDirectory = { navController.navigate(ROUTE_DIRECTORY) },
                    onNavigateToServices = { navController.navigate(ROUTE_SERVICES) }
                )
            }
            
            composable(ROUTE_DIRECTORY) {
                DirectoryScreen(viewModel = viewModel)
            }
            
            composable(ROUTE_SERVICES) {
                ServicesScreen(viewModel = viewModel)
            }
            
            composable(ROUTE_ABOUT) {
                AboutScreen(viewModel = viewModel)
            }
            
            composable(ROUTE_SETTINGS) {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
