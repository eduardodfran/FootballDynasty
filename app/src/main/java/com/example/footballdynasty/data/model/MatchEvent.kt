package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an event in a match (goal, card, injury, etc.)
 */
@Entity(
    tableName = "match_events",
    foreignKeys = [
        ForeignKey(
            entity = Match::class,
            parentColumns = ["matchId"],
            childColumns = ["matchId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["playerId"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["playerId"],
            childColumns = ["assistPlayerId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("matchId"),
        Index("playerId"),
        Index("assistPlayerId")
    ]
)
data class MatchEvent(
    @PrimaryKey(autoGenerate = true)
    val eventId: Long = 0,
    val matchId: Long,
    val minute: Int,
    val eventType: String, // GOAL, YELLOW_CARD, RED_CARD, INJURY, SUBSTITUTION
    val playerId: Long, // Player involved
    val assistPlayerId: Long? = null, // For goal assists
    val teamId: Long, // Team of the player
    val description: String? = null // Optional detailed description
)

// Event types as constants
object EventType {
    const val GOAL = "GOAL"
    const val YELLOW_CARD = "YELLOW_CARD"
    const val RED_CARD = "RED_CARD"
    const val INJURY = "INJURY"
    const val SUBSTITUTION = "SUBSTITUTION"
}
