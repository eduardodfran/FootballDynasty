package com.example.footballdynasty.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.footballdynasty.R
import com.example.footballdynasty.ui.components.PrimaryButton
import com.example.footballdynasty.ui.components.SecondaryButton
import com.example.footballdynasty.ui.theme.FootballDynastyTheme

@Composable
fun WelcomeScreen(
    onNewGameClick: () -> Unit = {},
    onContinueGameClick: () -> Unit = {},
    hasSavedGame: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Game Logo/Title
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Game Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 24.dp)
            )
            
            Text(
                text = "Football Dynasty",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Build your team, conquer the Philippines Football League, and create a dynasty!",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            PrimaryButton(
                text = "New Game",
                onClick = onNewGameClick
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (hasSavedGame) {
                SecondaryButton(
                    text = "Continue Game",
                    onClick = onContinueGameClick
                )
            }
        }
        
        // Version info at bottom
        Text(
            text = "Version 1.0",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    FootballDynastyTheme {
        WelcomeScreen(hasSavedGame = true)
    }
}
