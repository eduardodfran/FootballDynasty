package com.example.footballdynasty

import android.app.Application
import com.example.footballdynasty.data.database.FootballDatabase
import com.example.footballdynasty.data.repository.FootballRepository

class FootballApplication : Application() {
    
    // Database instance
    private val database by lazy { FootballDatabase.getDatabase(this) }
    
    // Repository instance
    val repository by lazy { FootballRepository(database.footballDao()) }
}
