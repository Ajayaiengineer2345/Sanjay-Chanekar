package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.model.GoogleCertification
import com.example.model.MarketingService
import com.example.ui.components.mapIconNameToVector
import com.example.viewmodel.DirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(viewModel: DirectoryViewModel) {
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    val scale = settingsState.fontSize.scale
    
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
                .testTag("services_screen_list")
        ) {
            // Screen Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(theme.secondary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = "SMM icon",
                                tint = theme.secondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "SANJAY CHANEKAR DIGITAL",
                                fontSize = (10 * scale).sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Professional SMM & Google Ads",
                        fontSize = (22 * scale).sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Grow your Nagpur business offline and online with tailored local marketing strategies.",
                        fontSize = (13 * scale).sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            // Sanjay's Services List Header
            item {
                Text(
                    text = "Core Marketing Packages",
                    fontSize = (16 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // Sanjay's Services
            items(viewModel.marketingServices) { service ->
                MarketingServiceCard(service = service, scale = scale, themeColor = theme.secondary, primaryColor = theme.primary)
            }

            // Certifications Header
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Google Certified Expertise",
                    fontSize = (16 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // Certifications Row/Item
            items(viewModel.certifications) { cert ->
                CertificationCard(cert = cert, scale = scale, glowColor = theme.glowColor)
            }

            // Quote Trigger Banner
            item {
                Spacer(modifier = Modifier.height(24.dp))
                LeadGenerationCard(scale = scale, primaryColor = theme.primary)
            }
        }
    }
}

@Composable
fun MarketingServiceCard(
    service: MarketingService,
    scale: Float,
    themeColor: Color,
    primaryColor: Color
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F1832)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                border = BorderStroke(
                    1.dp,
                    if (service.id == "serv_smm") Brush.linearGradient(colors = listOf(primaryColor, themeColor))
                    else Brush.linearGradient(colors = listOf(Color(0xFF1E293B), Color(0xFF1E293B)))
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(themeColor.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = mapIconNameToVector(service.iconName),
                        contentDescription = service.title,
                        tint = themeColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = service.title,
                        fontSize = (15 * scale).sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = service.price,
                            fontSize = (18 * scale).sp,
                            fontWeight = FontWeight.Black,
                            color = themeColor
                        )
                        Text(
                            text = " / ${service.duration}",
                            fontSize = (11 * scale).sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = service.description,
                fontSize = (12 * scale).sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Expandable Feature Checklist
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .background(Color(0xFF0A0F1E), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isExpanded) "Hide Package Details" else "View Package Details & Inclusions",
                    fontSize = (11 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = themeColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                ) {
                    service.features.forEach { feat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Included",
                                tint = themeColor,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = feat,
                                fontSize = (11 * scale).sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:sanjaychanekar@vidarbhads.com")).apply {
                                putExtra(Intent.EXTRA_SUBJECT, "Inquiry for ${service.title}")
                                putExtra(Intent.EXTRA_TEXT, "Hello Sanjay,\n\nI saw your ${service.title} package on the Nagpur Yellow Pages app. I am interested in growing my local business with you.\n\nPlease contact me back.")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Order Service Plan", fontSize = (12 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun CertificationCard(cert: GoogleCertification, scale: Float, glowColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F162A).copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(0.5.dp, glowColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(glowColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = mapIconNameToVector(cert.iconName),
                    contentDescription = "Verified badge",
                    tint = glowColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = cert.title,
                    fontSize = (13 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${cert.issuer} • ${cert.year}",
                    fontSize = (11 * scale).sp,
                    color = Color.Gray
                )
                Text(
                    text = cert.certUrl,
                    fontSize = (9 * scale).sp,
                    color = glowColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun LeadGenerationCard(scale: Float, primaryColor: Color) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0B1F3F)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color(0xFF1E3A8A), RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ChatBubbleOutline,
                contentDescription = "Inquire",
                tint = Color(0xFF00D2FF),
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Get a Free Marketing Audit",
                fontSize = (16 * scale).sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Sanjay Chanekar will review your current business profile and propose a local SEO roadmap for free.",
                fontSize = (12 * scale).sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919823011223?text=Hi%20Sanjay%2C%20I%20saw%20your%20Nagpur%20Yellow%20Pages%20app%20and%20want%20a%20free%20local%20marketing%20audit."))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PhoneInTalk, contentDescription = "Call", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Chat via WhatsApp", fontSize = (12 * scale).sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
