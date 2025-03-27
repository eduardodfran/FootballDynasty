package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a football team in the game
 */
@Entity(
    tableName = "teams",
    foreignKeys = [
        ForeignKey(
            entity = League::class,
            parentColumns = ["leagueId"],
            childColumns = ["leagueId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("leagueId")]
)
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamId: Long = 0,
    val name: String,
    val shortName: String,        // Abbreviated name (3-4 characters)
    val foundedYear: Int,
    val homeCity: String,
    val leagueId: Long,           // Foreign key to League
    var reputation: Int,          // 1-100 rating (affects player attraction)
    var fanSupport: Int,          // 1-100 rating (affects ticket sales)
    var balance: Long,            // Current financial balance
    var stadiumCapacity: Int,     // Current stadium size
    var stadiumQuality: Int,      // 1-100 rating (affects match day revenue)
    var trainingFacilityQuality: Int, // 1-100 rating (affects player development)
    var youthAcademyLevel: Int,   // 1-10 rating (affects youth player generation)
    var boardExpectation: String, // E.g., "Promotion", "Mid-table", "Avoid relegation"
    var managerRating: Int,       // Board's satisfaction with manager (1-100)
    var sponsorshipIncome: Long,  // Current sponsorship income per match
    var merchandiseIncome: Long,  // Current merchandise income per match
    var ticketIncome: Long,       // Average ticket income per match
    var transferBudget: Long,     // Available money for transfers
    var wageBudget: Long,         // Weekly wage budget
    var isPlayerControlled: Boolean = false // Whether this team is controlled by the player
)
