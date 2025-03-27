package com.example.footballdynasty.data.repository

import com.example.footballdynasty.data.dao.FootballDao
import com.example.footballdynasty.data.model.League
import com.example.footballdynasty.data.model.Player
import com.example.footballdynasty.data.model.Staff
import com.example.footballdynasty.data.model.Team
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides a clean API for data access to the rest of the application
 */
class FootballRepository(private val footballDao: FootballDao) {
    
    // League operations
    suspend fun insertLeague(league: League): Long = footballDao.insertLeague(league)
    suspend fun updateLeague(league: League) = footballDao.updateLeague(league)
    fun getAllLeagues(): Flow<List<League>> = footballDao.getAllLeagues()
    fun getLeagueById(leagueId: Long): Flow<League> = footballDao.getLeagueById(leagueId)
    
    // Team operations
    suspend fun insertTeam(team: Team): Long = footballDao.insertTeam(team)
    suspend fun updateTeam(team: Team) = footballDao.updateTeam(team)
    fun getAllTeams(): Flow<List<Team>> = footballDao.getAllTeams()
    fun getTeamById(teamId: Long): Flow<Team> = footballDao.getTeamById(teamId)
    fun getTeamsByLeague(leagueId: Long): Flow<List<Team>> = footballDao.getTeamsByLeague(leagueId)
    fun getPlayerControlledTeam(): Flow<Team?> = footballDao.getPlayerControlledTeam()
    
    // Player operations
    suspend fun insertPlayer(player: Player): Long = footballDao.insertPlayer(player)
    suspend fun updatePlayer(player: Player) = footballDao.updatePlayer(player)
    fun getAllPlayers(): Flow<List<Player>> = footballDao.getAllPlayers()
    fun getPlayerById(playerId: Long): Flow<Player> = footballDao.getPlayerById(playerId)
    fun getPlayersByTeam(teamId: Long): Flow<List<Player>> = footballDao.getPlayersByTeam(teamId)
    fun getFreeAgents(): Flow<List<Player>> = footballDao.getFreeAgents()
    
    // Staff operations
    suspend fun insertStaff(staff: Staff): Long = footballDao.insertStaff(staff)
    suspend fun updateStaff(staff: Staff) = footballDao.updateStaff(staff)
    fun getAllStaff(): Flow<List<Staff>> = footballDao.getAllStaff()
    fun getStaffById(staffId: Long): Flow<Staff> = footballDao.getStaffById(staffId)
    fun getStaffByTeam(teamId: Long): Flow<List<Staff>> = footballDao.getStaffByTeam(teamId)
    fun getStaffByTeamAndRole(teamId: Long, role: String): Flow<List<Staff>> = 
        footballDao.getStaffByTeamAndRole(teamId, role)
    fun getUnemployedStaff(): Flow<List<Staff>> = footballDao.getUnemployedStaff()
    
    // Game setup
    suspend fun setupNewGame(leagues: List<League>, teams: List<Team>, players: List<Player>, 
                             staffMembers: List<Staff>) {
        footballDao.setupNewGame(leagues, teams, players, staffMembers)
    }
}
