package com.example.footballdynasty

import android.app.Application
import com.example.footballdynasty.data.database.FootballDatabase
import com.example.footballdynasty.data.repository.FootballRepository
import com.example.footballdynasty.data.repository.MatchRepository

class FootballApplication : Application() {
    
    // Database instance
    private val database by lazy { FootballDatabase.getDatabase(this) }
    
    // Repository instances
    val repository by lazy { FootballRepository(database.footballDao()) }
    val matchRepository by lazy { MatchRepository(database.matchDao()) }
}
