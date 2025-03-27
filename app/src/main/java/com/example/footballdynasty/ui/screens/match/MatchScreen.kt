package com.example.footballdynasty.ui.screens.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.outlined.SportsScore
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.footballdynasty.data.model.EventType
import com.example.footballdynasty.data.model.Match
import com.example.footballdynasty.data.model.MatchEvent
import com.example.footballdynasty.data.model.PlayerMatchStats
import com.example.footballdynasty.ui.components.GameAppBar
import com.example.footballdynasty.ui.components.PrimaryButton
import com.example.footballdynasty.ui.theme.FootballDynastyTheme
import com.example.footballdynasty.util.SampleDataProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MatchScreen(
    match: Match,
    homeTeamName: String,
    awayTeamName: String,
    events: List<MatchEvent> = emptyList(),
    playerStats: List<PlayerMatchStats> = emptyList(),
    onPlayMatch: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            GameAppBar(
                title = "Match",
                showBackButton = true,
                onBackPressed = onBackPressed
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            MatchHeader(
                match = match,
                homeTeamName = homeTeamName,
                awayTeamName = awayTeamName
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (match.isPlayed) {
                MatchDetail(
                    match = match,
                    events = events,
                    playerStats = playerStats
                )
            } else {
                // Match not played yet
                UpcomingMatch(
                    match = match,
                    onPlayMatch = onPlayMatch
                )
            }
        }
    }
}

@Composable
fun MatchHeader(
    match: Match,
    homeTeamName: String,
    awayTeamName: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date and league info
            Text(
                text = formatMatchDate(match.matchDate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Week ${match.week}, Season ${match.season}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Team names and scores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(2f)
                ) {
                    TeamLogo(teamName = homeTeamName, size = 60.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = homeTeamName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Score
                if (match.isPlayed) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${match.homeGoals}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Text(
                                text = " - ",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Text(
                                text = "${match.awayGoals}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                // Away team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(2f)
                ) {
                    TeamLogo(teamName = awayTeamName, size = 60.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = awayTeamName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TeamLogo(
    teamName: String,
    size: androidx.compose.ui.unit.Dp
) {
    // Placeholder for team logo
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.SportsSoccer,
            contentDescription = "$teamName logo",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}

@Composable
fun UpcomingMatch(
    match: Match,
    onPlayMatch: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Icon(
            imageVector = Icons.Outlined.SportsScore,
            contentDescription = "Upcoming match",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Upcoming Match",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "This match has not been played yet.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = "Play Match",
            onClick = onPlayMatch,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Composable
fun MatchDetail(
    match: Match,
    events: List<MatchEvent>,
    playerStats: List<PlayerMatchStats>
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    
    val tabs = listOf("Summary", "Events", "Stats")
    
    Column(modifier = Modifier.fillMaxWidth()) {
        // Tab bar
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab content
        when (selectedTabIndex) {
            0 -> MatchSummary(match)
            1 -> MatchEvents(events)
            2 -> MatchStats(playerStats)
        }
    }
}

@Composable
fun MatchSummary(match: Match) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Match stats row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Possession",
                value = "${match.homePossession}% - ${100 - match.homePossession!!}%"
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Shots",
                homeValue = "${match.homeShots}",
                awayValue = "${match.awayShots}"
            )
            
            StatItem(
                label = "On Target",
                homeValue = "${match.homeShotsOnTarget}",
                awayValue = "${match.awayShotsOnTarget}"
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Fouls",
                homeValue = "${match.homeFouls}",
                awayValue = "${match.awayFouls}"
            )
            
            StatItem(
                label = "Yellow Cards",
                homeValue = "${match.homeYellowCards}",
                awayValue = "${match.awayYellowCards}"
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "Red Cards",
                homeValue = "${match.homeRedCards}",
                awayValue = "${match.awayRedCards}"
            )
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: String? = null,
    homeValue: String? = null,
    awayValue: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else if (homeValue != null && awayValue != null) {
            Row {
                Text(
                    text = homeValue,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = " - ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = awayValue,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun MatchEvents(events: List<MatchEvent>) {
    if (events.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No events recorded for this match",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn {
            items(events) { event ->
                EventItem(event)
                
                if (event != events.last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun EventItem(event: MatchEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Minute
        Box(
            modifier = Modifier
                .width(48.dp)
                .padding(end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "Minute",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(2.dp))
                
                Text(
                    text = "${event.minute}'",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Event icon
        val eventIcon = when (event.eventType) {
            EventType.GOAL -> Icons.Default.SportsSoccer
            EventType.YELLOW_CARD -> Icons.Outlined.SportsScore // Placeholder
            EventType.RED_CARD -> Icons.Outlined.SportsScore // Placeholder
            else -> Icons.Outlined.SportsScore // Placeholder
        }
        
        val eventColor = when (event.eventType) {
            EventType.GOAL -> MaterialTheme.colorScheme.primary
            EventType.YELLOW_CARD -> Color(0xFFFFC107) // Yellow
            EventType.RED_CARD -> Color(0xFFF44336) // Red
            else -> MaterialTheme.colorScheme.primary
        }
        
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(eventColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = eventIcon,
                contentDescription = event.eventType,
                tint = eventColor,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Event description
        Text(
            text = event.description ?: "Unknown event",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun MatchStats(playerStats: List<PlayerMatchStats>) {
    if (playerStats.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No player statistics recorded for this match",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Group by team and sort by rating
        val groupedStats = playerStats.groupBy { it.teamId }
            .mapValues { (_, players) -> players.sortedByDescending { it.rating } }
        
        LazyColumn {
            // Iterate through each team's players
            groupedStats.forEach { (teamId, players) ->
                // Team header
                item {
                    Text(
                        text = "Team $teamId", // Replace with actual team name in a real app
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    
                    // Column headers
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Player",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(2f)
                        )
                        
                        Text(
                            text = "Rating",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(0.7f),
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "G",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(0.5f),
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "A",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(0.5f),
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "Min",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
                
                // Player stats
                items(players) { stats ->
                    PlayerStatsRow(stats)
                }
                
                // Spacer between teams
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun PlayerStatsRow(stats: PlayerMatchStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // This would ideally use the player's name, but we'll use the ID for now
        // In a real app, you'd fetch the player's name using the ID
        Text(
            text = "Player ${stats.playerId} (${stats.position})",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(2f)
        )
        
        // Rating with color based on performance
        val ratingColor = when {
            stats.rating >= 8.0f -> MaterialTheme.colorScheme.primary
            stats.rating >= 7.0f -> Color(0xFF4CAF50) // Green
            stats.rating >= 6.0f -> Color(0xFFFFB74D) // Orange
            else -> Color(0xFFEF5350) // Red
        }
        
        Text(
            text = String.format("%.1f", stats.rating),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = ratingColor,
            modifier = Modifier.weight(0.7f),
            textAlign = TextAlign.Center
        )
        
        // Goals
        Text(
            text = "${stats.goals}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Center
        )
        
        // Assists
        Text(
            text = "${stats.assists}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Center
        )
        
        // Minutes played
        Text(
            text = "${stats.minutesPlayed}'",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(0.7f),
            textAlign = TextAlign.Center
        )
    }
}

fun formatMatchDate(date: Date): String {
    val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a", Locale.US)
    return dateFormat.format(date)
}

@Preview(showBackground = true)
@Composable
fun MatchScreenUpcomingPreview() {
    val sampleMatch = Match(
        matchId = 1,
        homeTeamId = 1,
        awayTeamId = 2,
        leagueId = 1,
        matchDate = Date(),
        week = 1,
        season = 1,
        isPlayed = false
    )
    
    FootballDynastyTheme {
        MatchScreen(
            match = sampleMatch,
            homeTeamName = "Manila FC",
            awayTeamName = "Cebu United"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MatchScreenPlayedPreview() {
    val sampleMatch = Match(
        matchId = 1,
        homeTeamId = 1,
        awayTeamId = 2,
        leagueId = 1,
        matchDate = Date(),
        week = 1,
        season = 1,
        isPlayed = true,
        homeGoals = 2,
        awayGoals = 1,
        homeShots = 12,
        awayShots = 8,
        homeShotsOnTarget = 6,
        awayShotsOnTarget = 3,
        homePossession = 58,
        homeFouls = 10,
        awayFouls = 12,
        homeYellowCards = 2,
        awayYellowCards = 3,
        homeRedCards = 0,
        awayRedCards = 1
    )
    
    FootballDynastyTheme {
        MatchScreen(
            match = sampleMatch,
            homeTeamName = "Manila FC",
            awayTeamName = "Cebu United",
            events = listOf(
                MatchEvent(
                    eventId = 1,
                    matchId = 1,
                    minute = 23,
                    eventType = EventType.GOAL,
                    playerId = 101,
                    teamId = 1,
                    description = "Santos scores for Manila FC"
                ),
                MatchEvent(
                    eventId = 2,
                    matchId = 1,
                    minute = 45,
                    eventType = EventType.YELLOW_CARD,
                    playerId = 201,
                    teamId = 2,
                    description = "Garcia receives a yellow card"
                ),
                MatchEvent(
                    eventId = 3,
                    matchId = 1,
                    minute = 67,
                    eventType = EventType.GOAL,
                    playerId = 102,
                    teamId = 1,
                    description = "Rodriguez scores for Manila FC"
                ),
                MatchEvent(
                    eventId = 4,
                    matchId = 1,
                    minute = 82,
                    eventType = EventType.GOAL,
                    playerId = 202,
                    teamId = 2,
                    description = "Fernandez scores for Cebu United"
                )
            )
        )
    }
}
