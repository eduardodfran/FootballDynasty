package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a football match in the game
 */
@Entity(
    tableName = "matches",
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = ["teamId"],
            childColumns = ["homeTeamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Team::class,
            parentColumns = ["teamId"],
            childColumns = ["awayTeamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = League::class,
            parentColumns = ["leagueId"],
            childColumns = ["leagueId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("homeTeamId"),
        Index("awayTeamId"),
        Index("leagueId")
    ]
)
data class Match(
    @PrimaryKey(autoGenerate = true)
    val matchId: Long = 0,
    val homeTeamId: Long,
    val awayTeamId: Long,
    val leagueId: Long,
    val matchDate: Date,
    val week: Int,
    val season: Int,
    var isPlayed: Boolean = false,
    
    // Match results (only populated after match is played)
    var homeGoals: Int? = null,
    var awayGoals: Int? = null,
    var homeShots: Int? = null,
    var awayShots: Int? = null,
    var homeShotsOnTarget: Int? = null,
    var awayShotsOnTarget: Int? = null,
    var homePossession: Int? = null, // Percentage value (0-100)
    var homeFouls: Int? = null,
    var awayFouls: Int? = null,
    var homeYellowCards: Int? = null,
    var awayYellowCards: Int? = null,
    var homeRedCards: Int? = null,
    var awayRedCards: Int? = null
)
