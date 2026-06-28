package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.DirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(viewModel: DirectoryViewModel) {
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
                .testTag("about_screen_list")
        ) {
            // Profile Card
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F1832), RoundedCornerShape(16.dp))
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Portrait Image (using our custom sanjay_avatar.jpg)
                    Image(
                        painter = painterResource(id = R.drawable.sanjay_avatar),
                        contentDescription = "Sanjay Chanekar profile",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, theme.secondary, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Sanjay Chanekar",
                        fontSize = (20 * scale).sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Text(
                        text = "Founder, VidarbhaDS & SMM Strategist",
                        fontSize = (13 * scale).sp,
                        color = theme.secondary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Providing offline businesses a solid digital footprint for over 9 years in Nagpur. Specialist in Social Media Marketing (SMM) and Google Ads campaigns.",
                        fontSize = (12 * scale).sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Contact Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919823011223"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call", fontSize = (11 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:sanjaychanekar@vidarbhads.com"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = "Email", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Email", fontSize = (11 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            // About Directory Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F1832).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = theme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "About Nagpur's Yellow Pages",
                            fontSize = (15 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Vidarbhads.com was founded with a singular vision: to democratize local search and business connectivity in Nagpur and the larger Vidarbha sector.\n\nOver the past 9 years, we have cataloged and verified more than 900+ active brick-and-mortar stores, engineering manufactures, and custom service shops, providing residents with lightning-fast offline directory searches and direct communication lines.",
                        fontSize = (12 * scale).sp,
                        color = Color.LightGray,
                        lineHeight = 18.sp
                    )
                }
            }

            // Address & Office Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F1832).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Office",
                            tint = theme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Registered Office",
                            fontSize = (15 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Vidarbha Directory Services\nPlot 14, Dharampeth Extension,\nNear Shankar Nagar Square, Nagpur, Maharashtra - 440010",
                        fontSize = (12 * scale).sp,
                        color = Color.LightGray,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:21.1402,79.0634?q=Shankar+Nagar+Nagpur"))
                            context.startActivity(intent)
                        },
                        border = BorderStroke(1.dp, theme.secondary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = theme.secondary)
                    ) {
                        Icon(Icons.Default.Map, contentDescription = "Map", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Map Location", fontSize = (11 * scale).sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Version Indicator
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "App Version 1.0.0 (Official • Production)\n© 2017-2026 VidarbhaDS. All rights reserved.",
                    fontSize = (10 * scale).sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
