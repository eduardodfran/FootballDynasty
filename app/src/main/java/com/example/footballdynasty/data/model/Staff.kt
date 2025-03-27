package com.example.footballdynasty.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a staff member in the game (manager, coach, scout, etc.)
 */
@Entity(
    tableName = "staff",
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
data class Staff(
    @PrimaryKey(autoGenerate = true)
    val staffId: Long = 0,
    val firstName: String,
    val lastName: String,
    val birthDate: Date,
    val nationality: String,
    val role: String,          // Head Coach, Assistant Coach, Scout, Physio, etc.
    var teamId: Long?,         // Foreign key to Team (nullable for unemployed staff)
    var wage: Int,             // Weekly wage
    var contractEndDate: Date?, // When contract expires
    
    // Role-specific Attributes (1-20 scale)
    var tactical: Int,         // Tactical knowledge
    var technical: Int,        // Technical training ability
    var mental: Int,           // Mental training ability
    var physical: Int,         // Physical training ability
    var youth: Int,            // Youth development
    var manManagement: Int,    // Player management
    var discipline: Int,       // Team discipline
    var attacking: Int,        // Attacking coaching
    var defending: Int,        // Defensive coaching
    var goalkeeper: Int,       // Goalkeeper coaching
    var medical: Int,          // Medical knowledge (for physios)
    var scouting: Int,         // Scouting ability
    var negotiation: Int,      // Contract negotiation
    
    // Only relevant for Head Coach
    var preferredFormation: String? = null,
    var playingStyle: String? = null    // Attacking, Balanced, Defensive, etc.
)
