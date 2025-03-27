package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a player in the game
 */
@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("teamId")]
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    val firstName: String,
    val lastName: String,
    val birthDate: Date,
    val nationality: String,
    val position: String,       // GK, DEF, MID, FWD
    val preferredFoot: String,  // Left, Right, Both
    val height: Int,            // Height in cm
    val weight: Int,            // Weight in kg
    var teamId: Long?,          // Foreign key to Team (nullable for free agents)
    var value: Long,            // Market value in currency
    var wage: Int,              // Weekly wage
    var contractEndDate: Date?, // When contract expires (null for free agents)
    var happiness: Int,         // 1-100 rating of player's happiness at club
    var condition: Int,         // 1-100 rating of current physical condition
    var injured: Boolean = false,
    var injuryDuration: Int = 0, // Weeks remaining of injury
    
    // Technical Attributes (1-20 scale)
    var passing: Int,
    var technique: Int,
    var dribbling: Int,
    var crossing: Int,
    var finishing: Int,
    var longShots: Int,
    var ballControl: Int,
    
    // Physical Attributes (1-20 scale)
    var acceleration: Int,
    var speed: Int,
    var strength: Int,
    var jumping: Int,
    var stamina: Int,
    var agility: Int,
    
    // Mental Attributes (1-20 scale)
    var workRate: Int,
    var vision: Int,
    var leadership: Int,
    var determination: Int,
    var decisions: Int,
    var teamwork: Int,
    
    // Goalkeeper Attributes (1-20 scale, only relevant for GKs)
    var reflexes: Int = 1,
    var handling: Int = 1,
    var goalkeepingPositioning: Int = 1,
    var aerialAbility: Int = 1,
    
    // Defensive Attributes (1-20 scale)
    var tackling: Int,
    var marking: Int,
    var positioning: Int,
    var interceptions: Int,
    
    // Development
    var potential: Int,         // 1-100 rating of maximum potential
    var development: Int,       // -10 to 10 modifier of development speed
    var isYouthPlayer: Boolean = false
)
