package com.example.footballdynasty.ui.screens.match

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.footballdynasty.FootballApplication
import com.example.footballdynasty.ui.viewmodels.MatchViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@Composable
fun MatchScreenComposable(
    matchId: Long,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val application = remember(context) {
        context.applicationContext as FootballApplication
    }
    
    val viewModel: MatchViewModel = viewModel(
        factory = MatchViewModel.Factory(
            matchId = matchId,
            matchRepository = application.matchRepository,
            footballRepository = application.repository
        )
    )
    
    val match by viewModel.match.collectAsState()
    val events by viewModel.events.collectAsState()
    val playerStats by viewModel.playerStats.collectAsState()
    val homeTeamName by viewModel.homeTeamName.collectAsState()
    val awayTeamName by viewModel.awayTeamName.collectAsState()
    val isSimulating by viewModel.isSimulating.collectAsState()
    
    Box(modifier = modifier.fillMaxSize()) {
        match?.let { matchData ->
            MatchScreen(
                match = matchData,
                homeTeamName = homeTeamName,
                awayTeamName = awayTeamName,
                events = events,
                playerStats = playerStats,
                onPlayMatch = { viewModel.simulateMatch() },
                onBackPressed = onBackPressed
            )
            
            if (isSimulating) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
