package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Represents a player's statistics for a specific match
 */
@Entity(
    tableName = "player_match_stats",
    primaryKeys = ["playerId", "matchId"],
    foreignKeys = [
        ForeignKey(
            entity = Player::class,
            parentColumns = ["playerId"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Match::class,
            parentColumns = ["matchId"],
            childColumns = ["matchId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Team::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("playerId"),
        Index("matchId"),
        Index("teamId")
    ]
)
data class PlayerMatchStats(
    val playerId: Long,
    val matchId: Long,
    val teamId: Long,
    val minutesPlayed: Int,
    val goals: Int = 0,
    val assists: Int = 0,
    val shots: Int = 0,
    val shotsOnTarget: Int = 0,
    val passes: Int = 0,
    val passAccuracy: Int = 0, // Percentage value (0-100)
    val tackles: Int = 0,
    val interceptions: Int = 0,
    val fouls: Int = 0,
    val yellowCards: Int = 0,
    val redCard: Boolean = false,
    val rating: Float = 0f, // Player rating out of 10
    val isStarting: Boolean = true,
    val position: String // Position played in this match
)
