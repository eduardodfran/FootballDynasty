package com.example.footballdynasty.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object CreateGame : Screen("create_game")
    object MainMenu : Screen("main_menu")
    object TeamOverview : Screen("team_overview")
    object Players : Screen("players")
    object Staff : Screen("staff")
    object Finance : Screen("finance")
    object Stadium : Screen("stadium")
    object League : Screen("league")
    object Match : Screen("match")
}
