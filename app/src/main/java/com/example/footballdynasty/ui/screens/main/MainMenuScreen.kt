package com.example.footballdynasty.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.SportsScore
import androidx.compose.material.icons.outlined.Stadium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.footballdynasty.ui.components.GameAppBar
import com.example.footballdynasty.ui.navigation.Screen
import com.example.footballdynasty.ui.theme.FootballDynastyTheme

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color
)

@Composable
fun MainMenuScreen(
    onNavigate: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val menuItems = listOf(
        MenuItem(
            title = "Team Overview",
            icon = Icons.Default.Home,
            route = Screen.TeamOverview.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "Players",
            icon = Icons.Default.Person,
            route = Screen.Players.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "Staff",
            icon = Icons.Outlined.Group,
            route = Screen.Staff.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "Finances",
            icon = Icons.Outlined.AttachMoney,
            route = Screen.Finance.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "Stadium",
            icon = Icons.Outlined.Stadium,
            route = Screen.Stadium.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "League",
            icon = Icons.Outlined.EmojiEvents,
            route = Screen.League.route,
            color = MaterialTheme.colorScheme.primary
        ),
        MenuItem(
            title = "Match",
            icon = Icons.Outlined.SportsScore,
            route = Screen.Match.route,
            color = MaterialTheme.colorScheme.primary
        )
    )
    
    Scaffold(
        topBar = {
            GameAppBar(
                title = "Football Dynasty",
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Season 1 • Week 1",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier
                                .clickable(onClick = onSettingsClick)
                                .padding(12.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Team summary card
            TeamSummaryCard(
                teamName = "Manila FC",
                managerName = "John Doe",
                budget = "$1,000,000",
                position = "2nd in PFL"
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Menu grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(menuItems) { item ->
                    MenuItemCard(
                        title = item.title,
                        icon = item.icon,
                        onClick = { onNavigate(item.route) },
                        color = item.color,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TeamSummaryCard(
    teamName: String,
    managerName: String,
    budget: String,
    position: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = teamName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Text(
                text = "Manager: $managerName",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row {
                StatusItem(
                    label = "Budget",
                    value = budget
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                StatusItem(
                    label = "Position",
                    value = position
                )
            }
        }
    }
}

@Composable
fun StatusItem(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun MenuItemCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    FootballDynastyTheme {
        MainMenuScreen()
    }
}
