package com.example.footballdynasty.data.repository

import com.example.footballdynasty.data.dao.MatchDao
import com.example.footballdynasty.data.model.Match
import com.example.footballdynasty.data.model.MatchEvent
import com.example.footballdynasty.data.model.PlayerMatchStats
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides a clean API for match-related data access
 */
class MatchRepository(private val matchDao: MatchDao) {
    
    // Match operations
    suspend fun insertMatch(match: Match): Long = matchDao.insertMatch(match)
    suspend fun insertMatches(matches: List<Match>): List<Long> = matchDao.insertMatches(matches)
    suspend fun updateMatch(match: Match) = matchDao.updateMatch(match)
    
    fun getMatchById(matchId: Long): Flow<Match> = matchDao.getMatchById(matchId)
    fun getMatchesByTeam(teamId: Long): Flow<List<Match>> = matchDao.getMatchesByTeam(teamId)
    fun getNextMatchForTeam(teamId: Long): Flow<Match?> = matchDao.getNextMatchForTeam(teamId)
    fun getLastMatchForTeam(teamId: Long): Flow<Match?> = matchDao.getLastMatchForTeam(teamId)
    fun getMatchesByLeagueAndSeason(leagueId: Long, season: Int): Flow<List<Match>> = 
        matchDao.getMatchesByLeagueAndSeason(leagueId, season)
    fun getMatchesByLeagueSeasonAndWeek(leagueId: Long, season: Int, week: Int): Flow<List<Match>> = 
        matchDao.getMatchesByLeagueSeasonAndWeek(leagueId, season, week)
    
    // Match event operations
    suspend fun insertMatchEvent(event: MatchEvent): Long = matchDao.insertMatchEvent(event)
    suspend fun insertMatchEvents(events: List<MatchEvent>): List<Long> = matchDao.insertMatchEvents(events)
    
    fun getEventsByMatch(matchId: Long): Flow<List<MatchEvent>> = matchDao.getEventsByMatch(matchId)
    fun getEventsByPlayer(playerId: Long): Flow<List<MatchEvent>> = matchDao.getEventsByPlayer(playerId)
    
    // Player match stats operations
    suspend fun insertPlayerStats(stats: PlayerMatchStats) = matchDao.insertPlayerStats(stats)
    suspend fun insertPlayersStats(stats: List<PlayerMatchStats>) = matchDao.insertPlayersStats(stats)
    
    fun getPlayerStatsByMatch(matchId: Long): Flow<List<PlayerMatchStats>> = 
        matchDao.getPlayerStatsByMatch(matchId)
    fun getStatsByPlayer(playerId: Long): Flow<List<PlayerMatchStats>> = 
        matchDao.getStatsByPlayer(playerId)
    fun getPlayerStatsForMatch(playerId: Long, matchId: Long): Flow<PlayerMatchStats?> = 
        matchDao.getPlayerStatsForMatch(playerId, matchId)
    
    // Save full match results
    suspend fun saveMatchResults(
        match: Match,
        events: List<MatchEvent>,
        playerStats: List<PlayerMatchStats>
    ) = matchDao.saveMatchResults(match, events, playerStats)
}
