package com.example.footballdynasty.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballdynasty.data.dao.FootballDao
import com.example.footballdynasty.data.model.League
import com.example.footballdynasty.data.model.Player
import com.example.footballdynasty.data.model.Staff
import com.example.footballdynasty.data.model.Team
import com.example.footballdynasty.data.util.Converters

@Database(
    entities = [League::class, Team::class, Player::class, Staff::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FootballDatabase : RoomDatabase() {
    
    abstract fun footballDao(): FootballDao
    
    companion object {
        @Volatile
        private var INSTANCE: FootballDatabase? = null
        
        fun getDatabase(context: Context): FootballDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FootballDatabase::class.java,
                    "football_dynasty_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
