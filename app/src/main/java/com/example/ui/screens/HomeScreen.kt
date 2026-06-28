package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Business
import com.example.model.BusinessCategory
import com.example.ui.components.mapIconNameToVector
import com.example.ui.components.parseColor
import com.example.viewmodel.DirectoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DirectoryViewModel,
    onNavigateToDirectory: () -> Unit,
    onNavigateToServices: () -> Unit
) {
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    val scale = settingsState.fontSize.scale
    
    // Gradient backgrounds based on selected theme
    val mainBgBrush = Brush.verticalGradient(
        colors = listOf(theme.background, theme.background.copy(alpha = 0.95f))
    )

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(mainBgBrush)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding() + 80.dp // extra spacing for bottom nav
            ),
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_screen_column")
        ) {
            // 1. Hero Header Banner
            item {
                HeroHeaderSection(
                    viewModel = viewModel,
                    onNavigateToDirectory = onNavigateToDirectory,
                    scale = scale
                )
            }

            // 2. Statistics Row
            item {
                StatsGridSection(scale = scale)
            }

            // 3. Category Grid Selector
            item {
                CategoriesSection(
                    categories = viewModel.categories,
                    scale = scale,
                    onCategoryClick = { categoryName ->
                        viewModel.selectCategory(categoryName)
                        onNavigateToDirectory()
                    }
                )
            }

            // 4. Promotions Highlight
            item {
                PromotionalBanner(
                    onNavigateToServices = onNavigateToServices,
                    scale = scale
                )
            }

            // 5. Featured Listings Section
            item {
                SectionHeader(
                    title = "Featured Nagpur Listings",
                    subtitle = "Verified local businesses & industrial giants",
                    scale = scale
                )
            }

            val featuredBusinesses = viewModel.allBusinesses.filter { it.isFeatured }
            items(featuredBusinesses) { business ->
                FeaturedBusinessCard(
                    business = business,
                    scale = scale,
                    onNavigate = onNavigateToDirectory
                )
            }

            // Extra padding
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun HeroHeaderSection(
    viewModel: DirectoryViewModel,
    onNavigateToDirectory: () -> Unit,
    scale: Float
) {
    val settingsState by viewModel.settingsState.collectAsState()
    val theme = settingsState.selectedTheme
    val animMultiplier = settingsState.animationSpeedMultiplier
    
    // Pulsing cyan glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween((1200 / animMultiplier).toInt(), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .shadow(16.dp, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        // High-fidelity background image
        Image(
            painter = painterResource(id = R.drawable.hero_banner),
            contentDescription = "Nagpur local business networking banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay with gradient + theme glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            theme.background.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // Nagpur Cyber Grid Lines Overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridSpacing = 30.dp.toPx()
            val gridColor = theme.secondary.copy(alpha = 0.08f * pulseAlpha)
            
            // Vertical grid lines
            var x = 0f
            while (x < size.width) {
                drawLine(
                    color = gridColor,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1.dp.toPx()
                )
                x += gridSpacing
            }
            
            // Horizontal grid lines
            var y = 0f
            while (y < size.height) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
                y += gridSpacing
            }
        }

        // Header Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Nagpur yellow pages logo",
                    tint = theme.secondary,
                    modifier = Modifier
                        .size(32.dp)
                        .shadow(8.dp * pulseAlpha, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Vidarbha Directory Services",
                        fontSize = (22 * scale).sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Nagpur's Premium Search Engine • Since 2017",
                        fontSize = (12 * scale).sp,
                        color = theme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Interactive Smart Search Bar
            var tempQuery by remember { mutableStateOf("") }
            OutlinedTextField(
                value = tempQuery,
                onValueChange = { tempQuery = it },
                placeholder = { 
                    Text("Search 900+ verified businesses in Nagpur...", fontSize = (14 * scale).sp, color = Color.White.copy(alpha = 0.6f)) 
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = theme.secondary) },
                trailingIcon = {
                    if (tempQuery.isNotEmpty()) {
                        IconButton(onClick = { tempQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = Color.LightGray)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = theme.surface.copy(alpha = 0.9f),
                    unfocusedContainerColor = theme.surface.copy(alpha = 0.75f),
                    focusedBorderColor = theme.secondary,
                    unfocusedBorderColor = theme.primary.copy(alpha = 0.5f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_search_bar")
                    .border(1.dp, theme.secondary.copy(alpha = 0.4f * pulseAlpha), RoundedCornerShape(32.dp)),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                ),
                keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                    onSearch = {
                        viewModel.updateSearchQuery(tempQuery)
                        viewModel.selectCategory(null)
                        onNavigateToDirectory()
                    }
                )
            )
        }
    }
}

@Composable
fun StatsGridSection(scale: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatItemCard(title = "900+", label = "Verified Businesses", flex = 1f, scale = scale)
        StatItemCard(title = "12+", label = "Categories", flex = 1f, scale = scale)
        StatItemCard(title = "9 Yrs", label = "Local Trust", flex = 1f, scale = scale)
    }
}

@Composable
fun RowScope.StatItemCard(title: String, label: String, flex: Float, scale: Float) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F162A).copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .weight(flex)
            .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = (18 * scale).sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF00D2FF),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = (10 * scale).sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CategoriesSection(
    categories: List<BusinessCategory>,
    scale: Float,
    onCategoryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        SectionHeader(
            title = "Explore Nagpur Directory",
            subtitle = "Sift through 12 industrial & commercial categories",
            scale = scale
        )
        
        // Horizontal list of Categories for simple layout
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                CategoryChipCard(category = category, scale = scale, onClick = onCategoryClick)
            }
        }
    }
}

@Composable
fun CategoryChipCard(
    category: BusinessCategory,
    scale: Float,
    onClick: (String) -> Unit
) {
    val catColor = parseColor(category.accentColorHex)
    
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E172C)
        ),
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick(category.name) }
            .border(1.dp, catColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .testTag("category_card_${category.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(catColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = mapIconNameToVector(category.iconName),
                    contentDescription = category.name,
                    tint = catColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = category.name,
                fontSize = (13 * scale).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${category.count} Listings",
                fontSize = (11 * scale).sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PromotionalBanner(
    onNavigateToServices: () -> Unit,
    scale: Float
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E1A33)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                border = BorderStroke(
                    1.dp,
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0072FF), Color(0xFF00D2FF))
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF00D2FF).copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "GROW YOUR BUSINESS",
                        fontSize = (9 * scale).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00D2FF)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Need Sanjay Chanekar's SMM Services?",
                    fontSize = (15 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Professional Social Media (₹5000/mo) & Google Ads specialist in Nagpur.",
                    fontSize = (11 * scale).sp,
                    color = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onNavigateToServices,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0072FF)
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text("View", fontSize = (12 * scale).sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    scale: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = (18 * scale).sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = subtitle,
            fontSize = (12 * scale).sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FeaturedBusinessCard(
    business: Business,
    scale: Float,
    onNavigate: () -> Unit
) {
    val bizColor = parseColor(business.accentColorHex)
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0E172C).copy(alpha = 0.85f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onNavigate() }
            .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
            .testTag("featured_card_${business.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    // Render left-side color-coded accent bar
                    drawLine(
                        color = bizColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 5.dp.toPx()
                    )
                }
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = business.name,
                        fontSize = (15 * scale).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f, fill = false),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(bizColor.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .border(0.5.dp, bizColor.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "FEATURED",
                            fontSize = (8 * scale).sp,
                            fontWeight = FontWeight.Bold,
                            color = bizColor
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFF1C40F),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${business.rating} (${business.reviewsCount} reviews)",
                        fontSize = (11 * scale).sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "•  ${business.category}",
                        fontSize = (11 * scale).sp,
                        color = bizColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = business.description,
                    fontSize = (12 * scale).sp,
                    color = Color.LightGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(10.dp))
                
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
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Quick Call Button
            IconButton(
                onClick = {
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_DIAL,
                        android.net.Uri.parse("tel:${business.phone}")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .background(Color(0xFF0F1A35), RoundedCornerShape(10.dp))
                    .border(0.5.dp, bizColor.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Quick Call",
                    tint = bizColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
