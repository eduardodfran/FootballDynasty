package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a football league in the game (PFL or Lower Division)
 */
@Entity(tableName = "leagues")
data class League(
    @PrimaryKey(autoGenerate = true)
    val leagueId: Long = 0,
    val name: String,
    val tier: Int, // 1 = PFL (top tier), 2 = Lower Division
    val seasonStartMonth: Int, // 1-12 representing month
    val seasonEndMonth: Int,   // 1-12 representing month
    val maxTeams: Int,         // Number of teams in the league (15 for PFL, 20 for Lower Division)
    val promotionSpots: Int,   // Number of teams promoted at season end
    val relegationSpots: Int,  // Number of teams relegated at season end
    val prize: Long,           // Prize money for winning the league
    val continentalQualificationSpots: Int = 0, // Teams qualifying for continental competitions
    val prestige: Int,         // 1-100 rating of league prestige (affects player attraction)
    val matchesPerSeason: Int  // Total matches per team in a season
)
