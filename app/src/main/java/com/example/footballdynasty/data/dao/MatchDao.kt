package com.example.footballdynasty.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.footballdynasty.data.model.Match
import com.example.footballdynasty.data.model.MatchEvent
import com.example.footballdynasty.data.model.PlayerMatchStats
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    // Match operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<Match>): List<Long>
    
    @Update
    suspend fun updateMatch(match: Match)
    
    @Query("SELECT * FROM matches WHERE matchId = :matchId")
    fun getMatchById(matchId: Long): Flow<Match>
    
    @Query("SELECT * FROM matches WHERE homeTeamId = :teamId OR awayTeamId = :teamId ORDER BY matchDate ASC")
    fun getMatchesByTeam(teamId: Long): Flow<List<Match>>
    
    @Query("SELECT * FROM matches WHERE (homeTeamId = :teamId OR awayTeamId = :teamId) AND isPlayed = 0 ORDER BY matchDate ASC LIMIT 1")
    fun getNextMatchForTeam(teamId: Long): Flow<Match?>
    
    @Query("SELECT * FROM matches WHERE (homeTeamId = :teamId OR awayTeamId = :teamId) AND isPlayed = 1 ORDER BY matchDate DESC LIMIT 1")
    fun getLastMatchForTeam(teamId: Long): Flow<Match?>
    
    @Query("SELECT * FROM matches WHERE leagueId = :leagueId AND season = :season ORDER BY week ASC, matchDate ASC")
    fun getMatchesByLeagueAndSeason(leagueId: Long, season: Int): Flow<List<Match>>
    
    @Query("SELECT * FROM matches WHERE leagueId = :leagueId AND season = :season AND week = :week ORDER BY matchDate ASC")
    fun getMatchesByLeagueSeasonAndWeek(leagueId: Long, season: Int, week: Int): Flow<List<Match>>
    
    // MatchEvent operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatchEvent(matchEvent: MatchEvent): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatchEvents(matchEvents: List<MatchEvent>): List<Long>
    
    @Query("SELECT * FROM match_events WHERE matchId = :matchId ORDER BY minute ASC")
    fun getEventsByMatch(matchId: Long): Flow<List<MatchEvent>>
    
    @Query("SELECT * FROM match_events WHERE playerId = :playerId")
    fun getEventsByPlayer(playerId: Long): Flow<List<MatchEvent>>
    
    // PlayerMatchStats operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerStats(playerStats: PlayerMatchStats)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayersStats(playersStats: List<PlayerMatchStats>)
    
    @Query("SELECT * FROM player_match_stats WHERE matchId = :matchId")
    fun getPlayerStatsByMatch(matchId: Long): Flow<List<PlayerMatchStats>>
    
    @Query("SELECT * FROM player_match_stats WHERE playerId = :playerId")
    fun getStatsByPlayer(playerId: Long): Flow<List<PlayerMatchStats>>
    
    @Query("SELECT * FROM player_match_stats WHERE playerId = :playerId AND matchId = :matchId")
    fun getPlayerStatsForMatch(playerId: Long, matchId: Long): Flow<PlayerMatchStats?>
    
    // Transaction to save full match results
    @Transaction
    suspend fun saveMatchResults(
        match: Match,
        events: List<MatchEvent>,
        playerStats: List<PlayerMatchStats>
    ) {
        updateMatch(match)
        insertMatchEvents(events)
        insertPlayersStats(playerStats)
    }
}
