package com.example.ironmantrainingapp.ui.aicoach

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ironmantrainingapp.data.UserProfile
import com.example.ironmantrainingapp.ui.theme.IronmanBlack
import com.example.ironmantrainingapp.ui.theme.IronmanRed

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: String = "Just now"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCoachScreen(userProfile: UserProfile) {
    var inputMessage by remember { mutableStateOf("") }
    var chatMessages by remember {
        mutableStateOf(
            listOf(
                ChatMessage(
                    text = "Hello! I am your Ironman 70.3 AI Training Coach connected to our elite triathlon database. Based on your profile (${userProfile.age} yrs old, ${userProfile.daysPerWeek} days/wk training), how can I help you optimize your swim, bike, run, pacing, or recovery strategy today?",
                    isUser = false
                )
            )
        )
    }

    val quickPrompts = listOf(
        "Race Day Pacing Strategy",
        "How to avoid cramping on the bike",
        "Brick workout execution",
        "Heart rate zone training",
        "Swim technique & sighting",
        "Recovery & sleep tips"
    )

    fun handleSendMessage(query: String) {
        if (query.isBlank()) return
        val userMsg = ChatMessage(text = query, isUser = true)
        chatMessages = chatMessages + userMsg
        inputMessage = ""

        val responseText = when {
            query.contains("pace", ignoreCase = true) || query.contains("strategy", ignoreCase = true) ->
                "For an Ironman 70.3, pacing is everything. Swim at a comfortable, smooth aerobic rhythm without burning shoulders. On the 90km bike, maintain 75-80% of your FTP (Zone 2/3) — do not surge on hills. For the 21.1km run, start controlled for the first 5km and settle into your half-marathon race pace."
            query.contains("cramp", ignoreCase = true) ->
                "Cramping usually stems from electrolyte depletion or riding above your threshold. Ensure you maintain adequate sodium balance and keep your cadence smooth (85-95 RPM) on the bike."
            query.contains("brick", ignoreCase = true) ->
                "Brick workouts (bike followed immediately by run) train your neuromuscular system for 'heavy legs'. Keep the first 1-2km of the run controlled as your legs adjust from cycling to running biomechanics."
            query.contains("heart rate", ignoreCase = true) || query.contains("zone", ignoreCase = true) ->
                "Spend ~80% of your weekly training volume in Zone 2 (aerobic base building) and only 15-20% in high-intensity Zones 4/5. This builds the fat-oxidation capacity needed for 70.3 endurance."
            query.contains("swim", ignoreCase = true) ->
                "Focus on bilateral breathing, high elbow catch, and sighting every 20-30 strokes in open water to keep a straight line."
            else ->
                "Great question! According to our Ironman 70.3 training database, consistency and proper recovery are key for your ${userProfile.daysPerWeek}-day weekly schedule. Focus on sleep, mobility, and executing your Zone 2 aerobic workouts diligently."
        }

        val aiMsg = ChatMessage(text = responseText, isUser = false)
        chatMessages = chatMessages + aiMsg
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AI TRIATHLON COACH",
                        style = MaterialTheme.typography.labelMedium,
                        color = IronmanRed,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Training Intelligence",
                        style = MaterialTheme.typography.headlineMedium,
                        color = IronmanBlack
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = IronmanRed.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "ONLINE",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = IronmanRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Prompt Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(quickPrompts) { prompt ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { handleSendMessage(prompt) },
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = prompt,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = IronmanBlack,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chat Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(chatMessages) { msg ->
                    ChatBubble(message = msg)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    placeholder = { Text("Ask AI Coach about training, pacing, workouts...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IronmanRed,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = { handleSendMessage(inputMessage) },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(IronmanRed)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.isUser
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier.widthIn(max = 300.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) IronmanRed else MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(14.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isUser) Color.White else IronmanBlack
            )
        }
    }
}
