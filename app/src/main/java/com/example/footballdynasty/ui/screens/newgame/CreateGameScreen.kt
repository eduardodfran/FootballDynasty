package com.example.footballdynasty.ui.screens.newgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.footballdynasty.data.model.Team
import com.example.footballdynasty.ui.components.GameAppBar
import com.example.footballdynasty.ui.components.PrimaryButton
import com.example.footballdynasty.ui.components.TeamCard
import com.example.footballdynasty.ui.theme.FootballDynastyTheme
import com.example.footballdynasty.util.SampleDataProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(
    onBackPressed: () -> Unit = {},
    onStartGame: (managerName: String, teamId: Long) -> Unit = { _, _ -> }
) {
    var managerName by remember { mutableStateOf("") }
    var selectedTeamId by remember { mutableStateOf<Long?>(null) }
    
    val availableTeams = SampleDataProvider.getSampleTeams()
    
    Scaffold(
        topBar = {
            GameAppBar(
                title = "Create New Game",
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
            Text(
                text = "Manager Information",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = managerName,
                onValueChange = { managerName = it },
                label = { Text("Manager Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Select Your Team",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(availableTeams) { team ->
                    TeamCard(
                        team = team,
                        onClick = { selectedTeamId = team.teamId },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            PrimaryButton(
                text = "Start Career",
                onClick = { 
                    selectedTeamId?.let { teamId ->
                        onStartGame(managerName, teamId) 
                    }
                },
                enabled = managerName.isNotBlank() && selectedTeamId != null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateGameScreenPreview() {
    FootballDynastyTheme {
        CreateGameScreen()
    }
}
