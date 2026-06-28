package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.state.AppTheme
import com.example.state.FontSize
import com.example.viewmodel.DirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: DirectoryViewModel) {
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    val scale = settingsState.fontSize.scale
    val context = LocalContext.current

    val bgBrush = Brush.verticalGradient(
        colors = listOf(theme.background, theme.background.copy(alpha = 0.95f))
    )

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 100.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .testTag("settings_screen_list")
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings Icon",
                        tint = theme.secondary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Advanced App Settings",
                        fontSize = (20 * scale).sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = "Customize themes, search radius, and notifications",
                        fontSize = (12 * scale).sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Group 1: Appearance
            item {
                SettingsGroupHeader(title = "Appearance & Interface", icon = Icons.Default.Palette, themeColor = theme.secondary, scale = scale)
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1832).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // 5 Theme options list
                        Text(
                            text = "Select Application Theme",
                            fontSize = (12 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Scrollable / grid theme list
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            AppTheme.values().forEach { appTheme ->
                                val isSelected = appTheme == theme
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (isSelected) theme.primary.copy(alpha = 0.15f) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) theme.secondary else Color(0xFF1E293B),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { viewModel.updateTheme(appTheme) }
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Colored Dot showing primary & secondary
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(appTheme.primary, appTheme.secondary)
                                                )
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = appTheme.displayName,
                                        fontSize = (13 * scale).sp,
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                        color = Color.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = theme.secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Dark mode toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Aesthetic Dark Mode", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Optimize colors for neon grid styling", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.isDarkMode,
                                onCheckedChange = { viewModel.toggleDarkMode(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = theme.secondary,
                                    checkedTrackColor = theme.primary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Font size selection
                        Text("Text Font Sizing", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FontSize.values().forEach { size ->
                                val isSelected = settingsState.fontSize == size
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            if (isSelected) theme.primary else Color(0xFF070B19),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) theme.secondary else Color(0xFF1E293B),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable { viewModel.updateFontSize(size) }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = size.displayName,
                                        fontSize = (12 * scale).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Animation speed slider
                        Text(
                            text = "Animation Speed: ${settingsState.animationSpeedMultiplier}x",
                            fontSize = (13 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Slider(
                            value = settingsState.animationSpeedMultiplier,
                            onValueChange = { viewModel.updateAnimationSpeed(it) },
                            valueRange = 0.5f..2.0f,
                            steps = 2,
                            colors = SliderDefaults.colors(
                                thumbColor = theme.secondary,
                                activeTrackColor = theme.primary
                            )
                        )
                    }
                }
            }

            // Group 2: Notifications
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SettingsGroupHeader(title = "Notification Rules", icon = Icons.Default.Notifications, themeColor = theme.secondary, scale = scale)
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1832).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Listing Verification Alerts", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Notify when yellow pages statuses are audited", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.isNotificationEnabled,
                                onCheckedChange = { viewModel.toggleNotifications(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = theme.secondary, checkedTrackColor = theme.primary)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Sanjay's SMM & SEO Tips", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Weekly notifications with advice on growing local store rankings", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.isMarketingTipsEnabled,
                                onCheckedChange = { viewModel.toggleMarketingTips(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = theme.secondary, checkedTrackColor = theme.primary)
                            )
                        }
                    }
                }
            }

            // Group 3: Search Preferences
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SettingsGroupHeader(title = "Search Parameters", icon = Icons.Default.TravelExplore, themeColor = theme.secondary, scale = scale)
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1832).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Local Nagpur Search Radius: ${settingsState.searchRadiusKm} km",
                            fontSize = (13 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Slider(
                            value = settingsState.searchRadiusKm.toFloat(),
                            onValueChange = { viewModel.updateSearchRadius(it.toInt()) },
                            valueRange = 1f..50f,
                            colors = SliderDefaults.colors(thumbColor = theme.secondary, activeTrackColor = theme.primary)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Auto-Suggestions engine", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Pre-load keyword listings matching keyboard input", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.autoSuggestionsEnabled,
                                onCheckedChange = { viewModel.toggleAutoSuggestions(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = theme.secondary, checkedTrackColor = theme.primary)
                            )
                        }
                    }
                }
            }

            // Group 4: Privacy
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SettingsGroupHeader(title = "Privacy Configuration", icon = Icons.Default.Lock, themeColor = theme.secondary, scale = scale)
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1832).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Share Anonymous Diagnostics", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Send anonymous logs to improve search rankings", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.shareAnalytics,
                                onCheckedChange = { viewModel.toggleShareAnalytics(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = theme.secondary, checkedTrackColor = theme.primary)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Personalize Ad Campaigns", fontSize = (13 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("Tailor SMM recommendations to browsed sectors", fontSize = (11 * scale).sp, color = Color.Gray)
                            }
                            Switch(
                                checked = settingsState.personalizationEnabled,
                                onCheckedChange = { viewModel.togglePersonalization(it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = theme.secondary, checkedTrackColor = theme.primary)
                            )
                        }
                    }
                }
            }

            // Group 5: App Info
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SettingsGroupHeader(title = "System Information", icon = Icons.Default.Info, themeColor = theme.secondary, scale = scale)
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1832).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Application Identifier", fontSize = (12 * scale).sp, color = Color.Gray)
                            Text("com.aistudio.vidarbhads.nhrywd", fontSize = (12 * scale).sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Technical Framework", fontSize = (12 * scale).sp, color = Color.Gray)
                            Text("Jetpack Compose & Kotlin", fontSize = (12 * scale).sp, color = theme.secondary, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Creator / Publisher", fontSize = (12 * scale).sp, color = Color.Gray)
                            Text("Sanjay Chanekar (Nagpur)", fontSize = (12 * scale).sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                Toast.makeText(context, "Local search cache successfully cleared!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD01B1B)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear cache", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Flush Directory Cache", fontSize = (12 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsGroupHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    themeColor: Color,
    scale: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = themeColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = (15 * scale).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
