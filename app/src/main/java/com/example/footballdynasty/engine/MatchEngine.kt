package com.example.footballdynasty.engine

import com.example.footballdynasty.data.model.EventType
import com.example.footballdynasty.data.model.Match
import com.example.footballdynasty.data.model.MatchEvent
import com.example.footballdynasty.data.model.Player
import com.example.footballdynasty.data.model.PlayerMatchStats
import com.example.footballdynasty.data.model.Team
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * Engine to simulate football matches based on team and player attributes
 */
class MatchEngine {
    
    /**
     * Simulates a match between two teams
     */
    fun simulateMatch(
        match: Match,
        homeTeam: Team,
        awayTeam: Team,
        homePlayers: List<Player>,
        awayPlayers: List<Player>
    ): MatchResult {
        // Basic strength calculations
        val homeStrength = calculateTeamStrength(homeTeam, homePlayers)
        val awayStrength = calculateTeamStrength(awayTeam, awayPlayers)
        
        // Add home advantage
        val homeAdvantage = 10
        val effectiveHomeStrength = homeStrength + homeAdvantage
        
        // Calculate possession, based on team strengths
        val totalStrength = effectiveHomeStrength + awayStrength
        val homePossession = (effectiveHomeStrength.toDouble() / totalStrength * 100).toInt()
        val awayPossession = 100 - homePossession
        
        // Generate shots based on possession and randomness
        val homeShots = max(5, generateShots(homePossession))
        val awayShots = max(5, generateShots(awayPossession))
        
        // Calculate shots on target (around 35-65% of total shots)
        val homeShotsOnTarget = (homeShots * Random.nextDouble(0.35, 0.65)).toInt()
        val awayShotsOnTarget = (awayShots * Random.nextDouble(0.35, 0.65)).toInt()
        
        // Calculate goals (conversion rate usually 10-30% of shots on target)
        val homeGoals = (homeShotsOnTarget * Random.nextDouble(0.1, 0.3)).toInt()
        val awayGoals = (awayShotsOnTarget * Random.nextDouble(0.1, 0.3)).toInt()
        
        // Generate fouls, cards based on teams' discipline ratings
        val homeFouls = generateFouls()
        val awayFouls = generateFouls()
        
        val homeYellowCards = min(5, (homeFouls * Random.nextDouble(0.2, 0.4)).toInt())
        val awayYellowCards = min(5, (awayFouls * Random.nextDouble(0.2, 0.4)).toInt())
        
        val homeRedCards = if (Random.nextDouble() < 0.08) 1 else 0
        val awayRedCards = if (Random.nextDouble() < 0.08) 1 else 0
        
        // Update match with results
        val updatedMatch = match.copy(
            isPlayed = true,
            homeGoals = homeGoals,
            awayGoals = awayGoals,
            homeShots = homeShots,
            awayShots = awayShots,
            homeShotsOnTarget = homeShotsOnTarget,
            awayShotsOnTarget = awayShotsOnTarget,
            homePossession = homePossession,
            homeFouls = homeFouls,
            awayFouls = awayFouls,
            homeYellowCards = homeYellowCards,
            awayYellowCards = awayYellowCards,
            homeRedCards = homeRedCards,
            awayRedCards = awayRedCards
        )
        
        // Generate events (goals, cards, etc.)
        val events = generateMatchEvents(
            match, 
            homeTeam, 
            awayTeam, 
            homePlayers, 
            awayPlayers, 
            homeGoals, 
            awayGoals, 
            homeYellowCards, 
            awayYellowCards, 
            homeRedCards, 
            awayRedCards
        )
        
        // Generate player stats
        val playerStats = generatePlayerStats(
            match, 
            homeTeam, 
            awayTeam, 
            homePlayers, 
            awayPlayers, 
            events, 
            homePossession
        )
        
        return MatchResult(updatedMatch, events, playerStats)
    }
    
    private fun calculateTeamStrength(team: Team, players: List<Player>): Int {
        // Calculate average player rating from key attributes
        val playerRatings = players.map { player -> 
            when (player.position) {
                "GK" -> (player.reflexes + player.handling + player.aerialAbility + 
                        player.goalkeepingPositioning) / 4.0
                "DEF" -> (player.tackling + player.marking + player.positioning + 
                        player.interceptions + player.strength) / 5.0
                "MID" -> (player.passing + player.technique + player.vision + 
                        player.decisions + player.stamina) / 5.0
                "FWD" -> (player.finishing + player.technique + player.dribbling + 
                        player.speed + player.ballControl) / 5.0
                else -> 10.0 // Default average value
            }
        }
        
        // Team strength from player average + team factors
        val avgPlayerRating = playerRatings.average() * 5 // Scale to 0-100
        val teamFactors = team.reputation * 0.5 + team.stadiumQuality * 0.3 + team.trainingFacilityQuality * 0.2
        
        return (avgPlayerRating + teamFactors).toInt()
    }
    
    private fun generateShots(possession: Int): Int {
        // Base shots (5-15) plus possession bonus
        val baseShotsLow = 5
        val baseShotsHigh = 15
        val possessionMultiplier = possession / 50.0
        
        return (Random.nextInt(baseShotsLow, baseShotsHigh) * possessionMultiplier).toInt()
    }
    
    private fun generateFouls(): Int {
        // Random fouls between 5-15
        return Random.nextInt(5, 16)
    }
    
    private fun generateMatchEvents(
        match: Match,
        homeTeam: Team,
        awayTeam: Team,
        homePlayers: List<Player>,
        awayPlayers: List<Player>,
        homeGoals: Int,
        awayGoals: Int,
        homeYellowCards: Int,
        awayYellowCards: Int,
        homeRedCards: Int,
        awayRedCards: Int
    ): List<MatchEvent> {
        val events = mutableListOf<MatchEvent>()
        val goalMinutes = generateUniqueMinutes(homeGoals + awayGoals, 1, 90)
        val yellowCardMinutes = generateUniqueMinutes(homeYellowCards + awayYellowCards, 15, 90)
        val redCardMinutes = generateUniqueMinutes(homeRedCards + awayRedCards, 30, 90)
        
        // Generate goal events
        var goalIndex = 0
        repeat(homeGoals) {
            val scorer = selectRandomPlayer(homePlayers.filter { it.position != "GK" }, "FWD")
            val assist = if (Random.nextDouble() < 0.7) {
                selectRandomPlayer(homePlayers.filter { it.playerId != scorer.playerId }, "MID")
            } else null
            
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = goalMinutes[goalIndex++],
                    eventType = EventType.GOAL,
                    playerId = scorer.playerId,
                    assistPlayerId = assist?.playerId,
                    teamId = homeTeam.teamId,
                    description = "${scorer.lastName} scores for ${homeTeam.name}${assist?.let { " (assist: ${it.lastName})" } ?: ""}"
                )
            )
        }
        
        repeat(awayGoals) {
            val scorer = selectRandomPlayer(awayPlayers.filter { it.position != "GK" }, "FWD")
            val assist = if (Random.nextDouble() < 0.7) {
                selectRandomPlayer(awayPlayers.filter { it.playerId != scorer.playerId }, "MID")
            } else null
            
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = goalMinutes[goalIndex++],
                    eventType = EventType.GOAL,
                    playerId = scorer.playerId,
                    assistPlayerId = assist?.playerId,
                    teamId = awayTeam.teamId,
                    description = "${scorer.lastName} scores for ${awayTeam.name}${assist?.let { " (assist: ${it.lastName})" } ?: ""}"
                )
            )
        }
        
        // Generate yellow card events
        var yellowCardIndex = 0
        repeat(homeYellowCards) {
            val player = selectRandomPlayer(homePlayers, null)
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = yellowCardMinutes[yellowCardIndex++],
                    eventType = EventType.YELLOW_CARD,
                    playerId = player.playerId,
                    teamId = homeTeam.teamId,
                    description = "${player.lastName} receives a yellow card"
                )
            )
        }
        
        repeat(awayYellowCards) {
            val player = selectRandomPlayer(awayPlayers, null)
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = yellowCardMinutes[yellowCardIndex++],
                    eventType = EventType.YELLOW_CARD,
                    playerId = player.playerId,
                    teamId = awayTeam.teamId,
                    description = "${player.lastName} receives a yellow card"
                )
            )
        }
        
        // Generate red card events
        var redCardIndex = 0
        repeat(homeRedCards) {
            val player = selectRandomPlayer(homePlayers, null)
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = redCardMinutes[redCardIndex++],
                    eventType = EventType.RED_CARD,
                    playerId = player.playerId,
                    teamId = homeTeam.teamId,
                    description = "${player.lastName} is sent off with a red card"
                )
            )
        }
        
        repeat(awayRedCards) {
            val player = selectRandomPlayer(awayPlayers, null)
            events.add(
                MatchEvent(
                    matchId = match.matchId,
                    minute = redCardMinutes[redCardIndex++],
                    eventType = EventType.RED_CARD,
                    playerId = player.playerId,
                    teamId = awayTeam.teamId,
                    description = "${player.lastName} is sent off with a red card"
                )
            )
        }
        
        // Sort events by minute
        return events.sortedBy { it.minute }
    }
    
    private fun generatePlayerStats(
        match: Match,
        homeTeam: Team,
        awayTeam: Team,
        homePlayers: List<Player>,
        awayPlayers: List<Player>,
        events: List<MatchEvent>,
        homePossession: Int
    ): List<PlayerMatchStats> {
        val playerStats = mutableListOf<PlayerMatchStats>()
        
        // Group events by player to calculate stats
        val eventsByPlayer = events.groupBy { it.playerId }
        val assistsByPlayer = events.filter { it.assistPlayerId != null }
            .groupBy { it.assistPlayerId!! }
        
        // Process home team players
        homePlayers.forEach { player ->
            val playerEvents = eventsByPlayer[player.playerId] ?: emptyList()
            val playerAssists = assistsByPlayer[player.playerId] ?: emptyList()
            
            val goals = playerEvents.count { it.eventType == EventType.GOAL }
            val assists = playerAssists.size
            val yellowCards = playerEvents.count { it.eventType == EventType.YELLOW_CARD }
            val redCard = playerEvents.any { it.eventType == EventType.RED_CARD }
            
            // Generate some realistic stats based on position and team's possession
            val passesMultiplier = when (player.position) {
                "GK" -> 0.3
                "DEF" -> 0.8
                "MID" -> 1.2
                "FWD" -> 0.6
                else -> 0.7
            }
            
            val passes = ((20 + Random.nextInt(0, 40)) * (homePossession / 50.0) * passesMultiplier).toInt()
            val passAccuracy = 50 + Random.nextInt(0, 41) // 50-90%
            
            val shotMultiplier = when (player.position) {
                "GK", "DEF" -> 0.1
                "MID" -> 0.5
                "FWD" -> 1.5
                else -> 0.3
            }
            
            val shots = ((1 + Random.nextInt(0, 5)) * shotMultiplier).toInt()
            val shotsOnTarget = min(shots, (shots * Random.nextDouble(0.3, 0.8)).toInt())
            
            val tackleMultiplier = when (player.position) {
                "GK" -> 0.0
                "DEF" -> 1.5
                "MID" -> 0.8
                "FWD" -> 0.3
                else -> 0.5
            }
            
            val tackles = ((1 + Random.nextInt(0, 8)) * tackleMultiplier).toInt()
            val interceptions = ((1 + Random.nextInt(0, 6)) * tackleMultiplier).toInt()
            
            // Calculate player rating (1-10 scale)
            val baseRating = 5.5f
            val goalBonus = goals * 1.0f
            val assistBonus = assists * 0.5f
            val yellowCardPenalty = yellowCards * -0.3f
            val redCardPenalty = if (redCard) -1.0f else 0f
            
            val performanceModifier = when (player.position) {
                "GK" -> Random.nextFloat() * 2 - 1 // -1 to 1
                "DEF" -> tackles * 0.1f + interceptions * 0.1f + Random.nextFloat() * 1.5f - 0.75f // -0.75 to 0.75
                "MID" -> passes * 0.01f + tackles * 0.05f + shotsOnTarget * 0.1f + Random.nextFloat() * 1.5f - 0.75f
                "FWD" -> shots * 0.05f + shotsOnTarget * 0.1f + Random.nextFloat() * 1.5f - 0.75f
                else -> Random.nextFloat() * 1.5f - 0.75f
            }
            
            var rating = baseRating + goalBonus + assistBonus + yellowCardPenalty + redCardPenalty + performanceModifier
            
            // Clamp rating between 1.0 and 10.0
            rating = rating.coerceIn(1.0f, 10.0f)
            
            playerStats.add(
                PlayerMatchStats(
                    playerId = player.playerId,
                    matchId = match.matchId,
                    teamId = homeTeam.teamId,
                    minutesPlayed = if (redCard) 
                        playerEvents.first { it.eventType == EventType.RED_CARD }.minute 
                    else 90,
                    goals = goals,
                    assists = assists,
                    shots = shots,
                    shotsOnTarget = shotsOnTarget,
                    passes = passes,
                    passAccuracy = passAccuracy,
                    tackles = tackles,
                    interceptions = interceptions,
                    fouls = if (yellowCards + (if (redCard) 1 else 0) > 0) 
                        1 + Random.nextInt(0, 4) 
                    else Random.nextInt(0, 3),
                    yellowCards = yellowCards,
                    redCard = redCard,
                    rating = rating,
                    position = player.position
                )
            )
        }
        
        // Process away team players (similar logic as home team)
        awayPlayers.forEach { player ->
            val playerEvents = eventsByPlayer[player.playerId] ?: emptyList()
            val playerAssists = assistsByPlayer[player.playerId] ?: emptyList()
            
            val goals = playerEvents.count { it.eventType == EventType.GOAL }
            val assists = playerAssists.size
            val yellowCards = playerEvents.count { it.eventType == EventType.YELLOW_CARD }
            val redCard = playerEvents.any { it.eventType == EventType.RED_CARD }
            
            val awayPossession = 100 - homePossession
            
            // Generate some realistic stats based on position and team's possession
            val passesMultiplier = when (player.position) {
                "GK" -> 0.3
                "DEF" -> 0.8
                "MID" -> 1.2
                "FWD" -> 0.6
                else -> 0.7
            }
            
            val passes = ((20 + Random.nextInt(0, 40)) * (awayPossession / 50.0) * passesMultiplier).toInt()
            val passAccuracy = 50 + Random.nextInt(0, 41) // 50-90%
            
            val shotMultiplier = when (player.position) {
                "GK", "DEF" -> 0.1
                "MID" -> 0.5
                "FWD" -> 1.5
                else -> 0.3
            }
            
            val shots = ((1 + Random.nextInt(0, 5)) * shotMultiplier).toInt()
            val shotsOnTarget = min(shots, (shots * Random.nextDouble(0.3, 0.8)).toInt())
            
            val tackleMultiplier = when (player.position) {
                "GK" -> 0.0
                "DEF" -> 1.5
                "MID" -> 0.8
                "FWD" -> 0.3
                else -> 0.5
            }
            
            val tackles = ((1 + Random.nextInt(0, 8)) * tackleMultiplier).toInt()
            val interceptions = ((1 + Random.nextInt(0, 6)) * tackleMultiplier).toInt()
            
            // Calculate player rating (1-10 scale)
            val baseRating = 5.5f
            val goalBonus = goals * 1.0f
            val assistBonus = assists * 0.5f
            val yellowCardPenalty = yellowCards * -0.3f
            val redCardPenalty = if (redCard) -1.0f else 0f
            
            val performanceModifier = when (player.position) {
                "GK" -> Random.nextFloat() * 2 - 1 // -1 to 1
                "DEF" -> tackles * 0.1f + interceptions * 0.1f + Random.nextFloat() * 1.5f - 0.75f // -0.75 to 0.75
                "MID" -> passes * 0.01f + tackles * 0.05f + shotsOnTarget * 0.1f + Random.nextFloat() * 1.5f - 0.75f
                "FWD" -> shots * 0.05f + shotsOnTarget * 0.1f + Random.nextFloat() * 1.5f - 0.75f
                else -> Random.nextFloat() * 1.5f - 0.75f
            }
            
            var rating = baseRating + goalBonus + assistBonus + yellowCardPenalty + redCardPenalty + performanceModifier
            
            // Clamp rating between 1.0 and 10.0
            rating = rating.coerceIn(1.0f, 10.0f)
            
            playerStats.add(
                PlayerMatchStats(
                    playerId = player.playerId,
                    matchId = match.matchId,
                    teamId = awayTeam.teamId,
                    minutesPlayed = if (redCard) 
                        playerEvents.first { it.eventType == EventType.RED_CARD }.minute 
                    else 90,
                    goals = goals,
                    assists = assists,
                    shots = shots,
                    shotsOnTarget = shotsOnTarget,
                    passes = passes,
                    passAccuracy = passAccuracy,
                    tackles = tackles,
                    interceptions = interceptions,
                    fouls = if (yellowCards + (if (redCard) 1 else 0) > 0) 
                        1 + Random.nextInt(0, 4) 
                    else Random.nextInt(0, 3),
                    yellowCards = yellowCards,
                    redCard = redCard,
                    rating = rating,
                    position = player.position
                )
            )
        }
        
        return playerStats
    }
    
    private fun generateUniqueMinutes(count: Int, min: Int, max: Int): List<Int> {
        val minutes = mutableSetOf<Int>()
        while (minutes.size < count) {
            minutes.add(Random.nextInt(min, max + 1))
        }
        return minutes.toList().sorted()
    }
    
    private fun selectRandomPlayer(
        players: List<Player>,
        preferredPosition: String?
    ): Player {
        // If preferred position specified, favor those players
        return if (preferredPosition != null) {
            val positionPlayers = players.filter { it.position == preferredPosition }
            if (positionPlayers.isNotEmpty() && Random.nextDouble() < 0.7) {
                positionPlayers.random()
            } else {
                players.random()
            }
        } else {
            players.random()
        }
    }
    
    /**
     * Contains the result of a match simulation
     */
    data class MatchResult(
        val match: Match,
        val events: List<MatchEvent>,
        val playerStats: List<PlayerMatchStats>
    )
}
