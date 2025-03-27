package com.example.footballdynasty.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.footballdynasty.data.model.League
import com.example.footballdynasty.data.model.Player
import com.example.footballdynasty.data.model.Staff
import com.example.footballdynasty.data.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface FootballDao {
    // League operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(league: League): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leagues: List<League>): List<Long>
    
    @Update
    suspend fun updateLeague(league: League)
    
    @Delete
    suspend fun deleteLeague(league: League)
    
    @Query("SELECT * FROM leagues")
    fun getAllLeagues(): Flow<List<League>>
    
    @Query("SELECT * FROM leagues WHERE leagueId = :leagueId")
    fun getLeagueById(leagueId: Long): Flow<League>
    
    // Team operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<Team>): List<Long>
    
    @Update
    suspend fun updateTeam(team: Team)
    
    @Delete
    suspend fun deleteTeam(team: Team)
    
    @Query("SELECT * FROM teams")
    fun getAllTeams(): Flow<List<Team>>
    
    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    fun getTeamById(teamId: Long): Flow<Team>
    
    @Query("SELECT * FROM teams WHERE leagueId = :leagueId")
    fun getTeamsByLeague(leagueId: Long): Flow<List<Team>>
    
    @Query("SELECT * FROM teams WHERE isPlayerControlled = 1 LIMIT 1")
    fun getPlayerControlledTeam(): Flow<Team?>
    
    // Player operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<Player>): List<Long>
    
    @Update
    suspend fun updatePlayer(player: Player)
    
    @Delete
    suspend fun deletePlayer(player: Player)
    
    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<Player>>
    
    @Query("SELECT * FROM players WHERE playerId = :playerId")
    fun getPlayerById(playerId: Long): Flow<Player>
    
    @Query("SELECT * FROM players WHERE teamId = :teamId")
    fun getPlayersByTeam(teamId: Long): Flow<List<Player>>
    
    @Query("SELECT * FROM players WHERE teamId IS NULL")
    fun getFreeAgents(): Flow<List<Player>>
    
    // Staff operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaff(staff: Staff): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStaffMembers(staffMembers: List<Staff>): List<Long>
    
    @Update
    suspend fun updateStaff(staff: Staff)
    
    @Delete
    suspend fun deleteStaff(staff: Staff)
    
    @Query("SELECT * FROM staff")
    fun getAllStaff(): Flow<List<Staff>>
    
    @Query("SELECT * FROM staff WHERE staffId = :staffId")
    fun getStaffById(staffId: Long): Flow<Staff>
    
    @Query("SELECT * FROM staff WHERE teamId = :teamId")
    fun getStaffByTeam(teamId: Long): Flow<List<Staff>>
    
    @Query("SELECT * FROM staff WHERE teamId = :teamId AND role = :role")
    fun getStaffByTeamAndRole(teamId: Long, role: String): Flow<List<Staff>>
    
    @Query("SELECT * FROM staff WHERE teamId IS NULL")
    fun getUnemployedStaff(): Flow<List<Staff>>
    
    // Transaction to setup a new game (create leagues, teams, etc.)
    @Transaction
    suspend fun setupNewGame(leagues: List<League>, teams: List<Team>, 
                             players: List<Player>, staffMembers: List<Staff>) {
        // Insert leagues
        val leagueIds = insertLeagues(leagues)
        
        // Update team leagueIds if needed and insert teams
        val updatedTeams = teams.mapIndexed { index, team ->
            if (team.leagueId == 0L) {
                // Assign to first league by default if not specified
                team.copy(leagueId = leagueIds.firstOrNull() ?: 1L)
            } else {
                team
            }
        }
        val teamIds = insertTeams(updatedTeams)
        
        // Insert players
        insertPlayers(players)
        
        // Insert staff
        insertStaffMembers(staffMembers)
    }
}
