package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Business
import com.example.ui.components.mapIconNameToVector
import com.example.ui.components.parseColor
import com.example.viewmodel.DirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(viewModel: DirectoryViewModel) {
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    val scale = settingsState.fontSize.scale
    
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    
    val filteredBusinesses = viewModel.getFilteredBusinesses()

    val bgBrush = Brush.verticalGradient(
        colors = listOf(theme.background, theme.background.copy(alpha = 0.96f))
    )

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding() + 80.dp // extra room for navigation bar
                )
        ) {
            // Screen Header & Search Box Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(theme.surface.copy(alpha = 0.3f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Nagpur Business Directory",
                    fontSize = (20 * scale).sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Text(
                    text = "${filteredBusinesses.size} active listings found in Nagpur",
                    fontSize = (12 * scale).sp,
                    color = theme.secondary,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    placeholder = { 
                        Text("Search by name, tags, or description...", fontSize = (14 * scale).sp, color = Color.Gray) 
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = theme.secondary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.LightGray)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = theme.surface,
                        unfocusedContainerColor = theme.surface.copy(alpha = 0.6f),
                        focusedBorderColor = theme.secondary,
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("directory_search_input")
                )
            }

            // Categories Filter Selector (Scrollable chips)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Clear chip
                item {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { viewModel.selectCategory(null) },
                        label = { Text("All Categories", fontSize = (12 * scale).sp) },
                        leadingIcon = { Icon(Icons.Default.Check, contentDescription = "Selected", modifier = Modifier.size(14.dp)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = theme.primary,
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White,
                            containerColor = theme.surface,
                            labelColor = Color.LightGray
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selected = selectedCategory == null,
                            enabled = true,
                            borderColor = Color(0xFF1E293B),
                            selectedBorderColor = theme.secondary,
                            borderWidth = 1.dp
                        )
                    )
                }

                items(viewModel.categories) { category ->
                    val isSelected = selectedCategory == category.name
                    val catColor = parseColor(category.accentColorHex)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectCategory(category.name) },
                        label = { Text(category.name, fontSize = (12 * scale).sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = catColor,
                            selectedLabelColor = Color.Black,
                            containerColor = theme.surface,
                            labelColor = Color.LightGray
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selected = isSelected,
                            enabled = true,
                            borderColor = if (isSelected) catColor else Color(0xFF1E293B),
                            borderWidth = 1.dp
                        )
                    )
                }
            }

            // Results Listing View
            if (filteredBusinesses.isEmpty()) {
                EmptyStateView(theme.secondary, scale)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .testTag("directory_list")
                ) {
                    items(filteredBusinesses) { business ->
                        DirectoryBusinessListItem(
                            business = business,
                            scale = scale,
                            themeColor = theme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(accentColor: Color, scale: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = "No listings found",
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Nagpur Listings Match",
            fontSize = (18 * scale).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Try clearing the search query or adjusting your category chips.",
            fontSize = (13 * scale).sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DirectoryBusinessListItem(
    business: Business,
    scale: Float,
    themeColor: Color
) {
    val context = LocalContext.current
    val bizColor = parseColor(business.accentColorHex)
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E172C).copy(alpha = 0.9f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
            .testTag("business_item_${business.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    // Left color accent bar
                    drawLine(
                        color = bizColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 5.dp.toPx()
                    )
                }
                .clickable { isExpanded = !isExpanded }
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = business.name,
                        fontSize = (16 * scale).sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF1C40F),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${business.rating} (${business.reviewsCount} reviews)",
                            fontSize = (11 * scale).sp,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "•  ${business.category}",
                            fontSize = (11 * scale).sp,
                            color = bizColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Expander arrow icon
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand details",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = business.description,
                fontSize = (12 * scale).sp,
                color = Color.LightGray,
                maxLines = if (isExpanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))
            
            // Location
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Address",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = business.address,
                    fontSize = (11 * scale).sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Expanded contacts and full links
            if (isExpanded) {
                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = Color(0xFF1E293B), thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(12.dp))

                // Detailed specs
                ContactRowItem(icon = Icons.Default.Call, label = "Phone", value = business.phone, scale = scale, onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${business.phone}"))
                    context.startActivity(intent)
                })
                Spacer(modifier = Modifier.height(8.dp))
                ContactRowItem(icon = Icons.Default.Email, label = "Email", value = business.email, scale = scale, onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${business.email}"))
                    context.startActivity(intent)
                })
                Spacer(modifier = Modifier.height(8.dp))
                ContactRowItem(icon = Icons.Default.Language, label = "Website", value = business.website, scale = scale, onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${business.website}"))
                    context.startActivity(intent)
                })

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${business.phone}"))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = bizColor),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call", fontSize = (12 * scale).sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    OutlinedButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, business.name)
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Check out ${business.name} in Nagpur directory!\nCategory: ${business.category}\nPhone: ${business.phone}\nWebsite: ${business.website}"
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Business"))
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFF1E293B)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share", fontSize = (12 * scale).sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    scale: Float,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF00D2FF),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "$label: ",
            fontSize = (12 * scale).sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
        Text(
            text = value,
            fontSize = (12 * scale).sp,
            color = Color.White,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Default.ArrowOutward,
            contentDescription = "Action",
            tint = Color.Gray,
            modifier = Modifier.size(12.dp)
        )
    }
}
