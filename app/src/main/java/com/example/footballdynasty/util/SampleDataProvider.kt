package com.example.footballdynasty.util

import com.example.footballdynasty.data.model.League
import com.example.footballdynasty.data.model.Team
import java.util.Date

/**
 * Provides sample data for UI development and testing
 */
object SampleDataProvider {
    
    fun getSampleLeagues(): List<League> {
        return listOf(
            League(
                leagueId = 1,
                name = "Philippines Football League",
                tier = 1,
                seasonStartMonth = 3,
                seasonEndMonth = 11,
                maxTeams = 15,
                promotionSpots = 0,
                relegationSpots = 3,
                prize = 100000,
                continentalQualificationSpots = 2,
                prestige = 80,
                matchesPerSeason = 28
            ),
            League(
                leagueId = 2,
                name = "Filipino Lower Division",
                tier = 2,
                seasonStartMonth = 3,
                seasonEndMonth = 11,
                maxTeams = 20,
                promotionSpots = 3,
                relegationSpots = 0,
                prize = 50000,
                continentalQualificationSpots = 0,
                prestige = 40,
                matchesPerSeason = 38
            )
        )
    }
    
    fun getSampleTeams(): List<Team> {
        return listOf(
            Team(
                teamId = 1,
                name = "Manila FC",
                shortName = "MFC",
                foundedYear = 1990,
                homeCity = "Manila",
                leagueId = 1,
                reputation = 85,
                fanSupport = 80,
                balance = 2000000,
                stadiumCapacity = 20000,
                stadiumQuality = 75,
                trainingFacilityQuality = 70,
                youthAcademyLevel = 7,
                boardExpectation = "Win the league",
                managerRating = 80,
                sponsorshipIncome = 100000,
                merchandiseIncome = 50000,
                ticketIncome = 75000,
                transferBudget = 1000000,
                wageBudget = 200000
            ),
            Team(
                teamId = 2,
                name = "Cebu United",
                shortName = "CEB",
                foundedYear = 1995,
                homeCity = "Cebu City",
                leagueId = 1,
                reputation = 78,
                fanSupport = 75,
                balance = 1800000,
                stadiumCapacity = 18000,
                stadiumQuality = 70,
                trainingFacilityQuality = 65,
                youthAcademyLevel = 6,
                boardExpectation = "Qualify for continental competition",
                managerRating = 75,
                sponsorshipIncome = 90000,
                merchandiseIncome = 45000,
                ticketIncome = 70000,
                transferBudget = 900000,
                wageBudget = 180000
            ),
            Team(
                teamId = 3,
                name = "Davao Dynamo",
                shortName = "DAV",
                foundedYear = 2000,
                homeCity = "Davao City",
                leagueId = 1,
                reputation = 72,
                fanSupport = 70,
                balance = 1600000,
                stadiumCapacity = 15000,
                stadiumQuality = 65,
                trainingFacilityQuality = 60,
                youthAcademyLevel = 5,
                boardExpectation = "Finish in top 5",
                managerRating = 70,
                sponsorshipIncome = 80000,
                merchandiseIncome = 40000,
                ticketIncome = 60000,
                transferBudget = 800000,
                wageBudget = 160000
            ),
            Team(
                teamId = 4,
                name = "Iloilo Warriors",
                shortName = "ILO",
                foundedYear = 2005,
                homeCity = "Iloilo City",
                leagueId = 1,
                reputation = 68,
                fanSupport = 65,
                balance = 1400000,
                stadiumCapacity = 12000,
                stadiumQuality = 60,
                trainingFacilityQuality = 55,
                youthAcademyLevel = 5,
                boardExpectation = "Mid-table finish",
                managerRating = 65,
                sponsorshipIncome = 70000,
                merchandiseIncome = 35000,
                ticketIncome = 50000,
                transferBudget = 700000,
                wageBudget = 140000
            ),
            Team(
                teamId = 5,
                name = "Bacolod Strikers",
                shortName = "BAC",
                foundedYear = 2010,
                homeCity = "Bacolod",
                leagueId = 2,
                reputation = 60,
                fanSupport = 55,
                balance = 1000000,
                stadiumCapacity = 10000,
                stadiumQuality = 50,
                trainingFacilityQuality = 45,
                youthAcademyLevel = 4,
                boardExpectation = "Promotion to PFL",
                managerRating = 60,
                sponsorshipIncome = 50000,
                merchandiseIncome = 25000,
                ticketIncome = 35000,
                transferBudget = 500000,
                wageBudget = 100000
            )
        )
    }
}
