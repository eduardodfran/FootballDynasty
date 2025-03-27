package com.example.footballdynasty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.footballdynasty.ui.navigation.Screen
import com.example.footballdynasty.ui.screens.main.MainMenuScreen
import com.example.footballdynasty.ui.screens.match.MatchScreenComposable
import com.example.footballdynasty.ui.screens.newgame.CreateGameScreen
import com.example.footballdynasty.ui.screens.welcome.WelcomeScreen
import com.example.footballdynasty.ui.theme.FootballDynastyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballDynastyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FootballDynastyApp()
                }
            }
        }
    }
}

@Composable
fun FootballDynastyApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNewGameClick = { navController.navigate(Screen.CreateGame.route) },
                onContinueGameClick = { navController.navigate(Screen.MainMenu.route) },
                hasSavedGame = false // This would be determined by checking for saved game data
            )
        }
        
        composable(Screen.CreateGame.route) {
            CreateGameScreen(
                onBackPressed = { navController.popBackStack() },
                onStartGame = { managerName, teamId ->
                    // In a real app, you would save the manager and team selection
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                onNavigate = { route -> navController.navigate(route) },
                onSettingsClick = { /* Open settings */ }
            )
        }
        
        // Placeholder screens - will be implemented later
        composable(Screen.TeamOverview.route) {
            // Placeholder for TeamOverviewScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Players.route) {
            // Placeholder for PlayersScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Staff.route) {
            // Placeholder for StaffScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Finance.route) {
            // Placeholder for FinanceScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Stadium.route) {
            // Placeholder for StadiumScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.League.route) {
            // Placeholder for LeagueScreen
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
        
        // Match screen with ID parameter
        composable(
            route = "${Screen.Match.route}/{matchId}",
            arguments = listOf(
                navArgument("matchId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getLong("matchId") ?: -1L
            MatchScreenComposable(
                matchId = matchId,
                onBackPressed = { navController.popBackStack() }
            )
        }
        
        // Generic match route (without ID) - could create a new match
        composable(Screen.Match.route) {
            // For demonstration purposes, we'll show a message to select a match
            WelcomeScreen(
                hasSavedGame = false,
                onNewGameClick = { navController.popBackStack() }
            )
        }
    }
}
