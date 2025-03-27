package com.example.footballdynasty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.footballdynasty.data.model.Match
import com.example.footballdynasty.data.model.MatchEvent
import com.example.footballdynasty.data.model.Player
import com.example.footballdynasty.data.model.PlayerMatchStats
import com.example.footballdynasty.data.model.Team
import com.example.footballdynasty.data.repository.FootballRepository
import com.example.footballdynasty.data.repository.MatchRepository
import com.example.footballdynasty.engine.MatchEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel to handle match simulation and related UI state
 */
class MatchViewModel(
    private val matchId: Long,
    private val matchRepository: MatchRepository,
    private val footballRepository: FootballRepository
) : ViewModel() {
    
    private val matchEngine = MatchEngine()
    
    // State for the match
    private val _match = MutableStateFlow<Match?>(null)
    val match: StateFlow<Match?> = _match.asStateFlow()
    
    // State for the events
    private val _events = MutableStateFlow<List<MatchEvent>>(emptyList())
    val events: StateFlow<List<MatchEvent>> = _events.asStateFlow()
    
    // State for player stats
    private val _playerStats = MutableStateFlow<List<PlayerMatchStats>>(emptyList())
    val playerStats: StateFlow<List<PlayerMatchStats>> = _playerStats.asStateFlow()
    
    // State for team names
    private val _homeTeamName = MutableStateFlow("")
    val homeTeamName: StateFlow<String> = _homeTeamName.asStateFlow()
    
    private val _awayTeamName = MutableStateFlow("")
    val awayTeamName: StateFlow<String> = _awayTeamName.asStateFlow()
    
    // State for simulation in progress
    private val _isSimulating = MutableStateFlow(false)
    val isSimulating: StateFlow<Boolean> = _isSimulating.asStateFlow()
    
    init {
        loadMatchData()
    }
    
    private fun loadMatchData() {
        viewModelScope.launch {
            try {
                // Load the match
                val matchData = matchRepository.getMatchById(matchId).first()
                _match.value = matchData
                
                // Load team names
                val homeTeam = footballRepository.getTeamById(matchData.homeTeamId).first()
                val awayTeam = footballRepository.getTeamById(matchData.awayTeamId).first()
                _homeTeamName.value = homeTeam.name
                _awayTeamName.value = awayTeam.name
                
                // If match is played, load events and player stats
                if (matchData.isPlayed) {
                    _events.value = matchRepository.getEventsByMatch(matchId).first()
                    _playerStats.value = matchRepository.getPlayerStatsByMatch(matchId).first()
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
    
    fun simulateMatch() {
        viewModelScope.launch {
            try {
                _isSimulating.value = true
                
                // Get current match data
                val matchData = _match.value ?: return@launch
                
                // Get teams and players for simulation
                val homeTeam = footballRepository.getTeamById(matchData.homeTeamId).first()
                val awayTeam = footballRepository.getTeamById(matchData.awayTeamId).first()
                val homePlayers = footballRepository.getPlayersByTeam(matchData.homeTeamId).first()
                val awayPlayers = footballRepository.getPlayersByTeam(matchData.awayTeamId).first()
                
                // Simulate the match
                val result = matchEngine.simulateMatch(
                    matchData,
                    homeTeam,
                    awayTeam,
                    homePlayers,
                    awayPlayers
                )
                
                // Save match results to database
                matchRepository.saveMatchResults(
                    result.match,
                    result.events,
                    result.playerStats
                )
                
                // Update UI state
                _match.value = result.match
                _events.value = result.events
                _playerStats.value = result.playerStats
                
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isSimulating.value = false
            }
        }
    }
    
    /**
     * Factory for creating MatchViewModel instances
     */
    class Factory(
        private val matchId: Long,
        private val matchRepository: MatchRepository,
        private val footballRepository: FootballRepository
    ) : ViewModelProvider.Factory {
        
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
                return MatchViewModel(matchId, matchRepository, footballRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
