package com.example.footballdynasty.util

import com.example.footballdynasty.data.model.Match
import java.util.Calendar
import java.util.Date

/**
 * Utility for generating sample matches for testing
 */
object SampleMatchGenerator {
    
    /**
     * Generates a schedule of matches for a league
     */
    fun generateLeagueSchedule(
        leagueId: Long,
        teamIds: List<Long>,
        season: Int,
        startDate: Date = Date()
    ): List<Match> {
        // Ensure we have an even number of teams (add a "bye" team if needed)
        val teamsForSchedule = if (teamIds.size % 2 == 1) {
            teamIds + 0L // 0 represents a bye
        } else {
            teamIds
        }
        
        val matches = mutableListOf<Match>()
        val numTeams = teamsForSchedule.size
        val numRounds = numTeams - 1
        val matchesPerRound = numTeams / 2
        
        // Create a round-robin schedule
        for (round in 0 until numRounds) {
            val calendar = Calendar.getInstance()
            calendar.time = startDate
            calendar.add(Calendar.DAY_OF_YEAR, round * 7) // One round per week
            val roundDate = calendar.time
            
            // Teams for this round
            val homeTeams = mutableListOf<Long>()
            val awayTeams = mutableListOf<Long>()
            
            // First team is fixed
            val firstTeam = teamsForSchedule[0]
            
            // Other teams rotate
            val rotating = teamsForSchedule.subList(1, teamsForSchedule.size).toMutableList()
            for (i in 0 until round) {
                rotating.add(0, rotating.removeAt(rotating.size - 1))
            }
            
            // Create pairings
            for (i in 0 until matchesPerRound) {
                if (i == 0) {
                    // First team plays against the first team in the rotating list
                    homeTeams.add(firstTeam)
                    awayTeams.add(rotating[0])
                } else {
                    // Other pairings
                    val homeIndex = i
                    val awayIndex = numTeams - 1 - i
                    homeTeams.add(rotating[homeIndex - 1])
                    awayTeams.add(rotating[awayIndex - 1])
                }
            }
            
            // Create matches for this round, filtering out any with the bye team (0)
            for (i in 0 until matchesPerRound) {
                val homeTeamId = homeTeams[i]
                val awayTeamId = awayTeams[i]
                
                // Skip matches with the bye team
                if (homeTeamId == 0L || awayTeamId == 0L) continue
                
                val matchHour = 15 + (i % 4) * 2 // Matches at 15:00, 17:00, 19:00, 21:00
                
                val matchCalendar = Calendar.getInstance()
                matchCalendar.time = roundDate
                matchCalendar.set(Calendar.HOUR_OF_DAY, matchHour)
                matchCalendar.set(Calendar.MINUTE, 0)
                matchCalendar.set(Calendar.SECOND, 0)
                
                matches.add(
                    Match(
                        homeTeamId = homeTeamId,
                        awayTeamId = awayTeamId,
                        leagueId = leagueId,
                        matchDate = matchCalendar.time,
                        week = round + 1,
                        season = season,
                        isPlayed = false
                    )
                )
            }
        }
        
        // Return first half of the season
        return matches
    }
    
    /**
     * Generates a single test match
     */
    fun generateSampleMatch(
        homeTeamId: Long,
        awayTeamId: Long,
        leagueId: Long,
        isPlayed: Boolean = false
    ): Match {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7) // Next week
        
        return Match(
            homeTeamId = homeTeamId,
            awayTeamId = awayTeamId,
            leagueId = leagueId,
            matchDate = calendar.time,
            week = 1,
            season = 1,
            isPlayed = isPlayed
        )
    }
}
