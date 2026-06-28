package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.Business
import com.example.model.BusinessCategory
import com.example.model.GoogleCertification
import com.example.model.MarketingService
import com.example.state.AppSettings
import com.example.state.AppTheme
import com.example.state.FontSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DirectoryViewModel : ViewModel() {

    // App Settings State
    private val _settingsState = MutableStateFlow(AppSettings())
    val settingsState: StateFlow<AppSettings> = _settingsState.asStateFlow()

    // Directory Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Master List of 12 Categories with realistic statistics
    val categories = listOf(
        BusinessCategory("cat_mfg", "Manufacturing & Industrial", "factory", 184, "#FF5722"),
        BusinessCategory("cat_it", "IT & Digital Services", "computer", 215, "#00E5FF"),
        BusinessCategory("cat_home", "Home Services & Repairs", "build", 162, "#4CAF50"),
        BusinessCategory("cat_furn", "Office Furniture & Interiors", "chair", 94, "#FFEB3B"),
        BusinessCategory("cat_prof", "Professional Services", "work", 198, "#E91E63"),
        BusinessCategory("cat_health", "Healthcare & Medical", "medical_services", 145, "#00E676"),
        BusinessCategory("cat_edu", "Education & Academy", "school", 188, "#9C27B0"),
        BusinessCategory("cat_real", "Real Estate & Builders", "home", 176, "#FF9800"),
        BusinessCategory("cat_auto", "Automobiles & Garage", "directions_car", 120, "#3F51B5"),
        BusinessCategory("cat_event", "Event Management & Catering", "celebration", 85, "#F44336"),
        BusinessCategory("cat_retail", "Retail & Wholesalers", "shopping_cart", 240, "#E040FB"),
        BusinessCategory("cat_hotel", "Hotels & Restaurants", "restaurant", 152, "#009688")
    )

    // Master List of Businesses (6 real featured from prompt + 6 realistic for other categories)
    val allBusinesses = listOf(
        // 1. Weldfast Electrodes
        Business(
            id = "biz_weldfast",
            name = "Weldfast Electrodes",
            category = "Manufacturing & Industrial",
            address = "F-22, MIDC Industrial Area, Hingna Road, Nagpur, 440016",
            phone = "+91 98230 11223",
            email = "info@weldfastelectrodes.com",
            website = "www.weldfastelectrodes.com",
            rating = 4.8f,
            reviewsCount = 124,
            description = "Established in 2012, Weldfast Electrodes is Nagpur's leading manufacturer of high-tensile welding electrodes, CO2 wire, MIG/TIG wires, and core wire supplies. Known for exceptional metallurgical quality and durability, serving major heavy machinery fabricators across Central India.",
            isFeatured = true,
            accentColorHex = "#FF5722"
        ),
        // 2. Air Force Compressors
        Business(
            id = "biz_airforce",
            name = "Air Force Compressors",
            category = "Manufacturing & Industrial",
            address = "Plot No. 45, Near Bus Stand, Ganeshpeth, Nagpur, 440018",
            phone = "+91 93710 44556",
            email = "sales@airforcecompressors.in",
            website = "www.airforcecompressors.in",
            rating = 4.7f,
            reviewsCount = 89,
            description = "Specialists in state-of-the-art high-performance industrial reciprocating air compressors, heavy-duty screw compressors, and pneumatic air dryer parts. Offering custom sizing, installation, and preventative maintenance for factories throughout the Vidarbha region.",
            isFeatured = true,
            accentColorHex = "#2196F3"
        ),
        // 3. M.H. Industries
        Business(
            id = "biz_mhindustries",
            name = "M.H. Industries",
            category = "Manufacturing & Industrial",
            address = "Survey No. 112, Wadi, Amravati Road, Nagpur, 440023",
            phone = "+91 94221 77889",
            email = "contact@mhindustries.co.in",
            website = "www.mhindustries.co.in",
            rating = 4.6f,
            reviewsCount = 54,
            description = "A premier structural steel fabrication shop specializing in CNC laser cutting, structural steel bendings, precision sheet metal works, and specialized welding. Outfitted with imported robotic machinery to deliver supreme accuracy for industrial components.",
            isFeatured = true,
            accentColorHex = "#9E9E9E"
        ),
        // 4. Sheetal Refrigeration
        Business(
            id = "biz_sheetal",
            name = "Sheetal Refrigeration",
            category = "Home Services & Repairs",
            address = "Shankar Nagar Square, Dharampeth, Nagpur, 440010",
            phone = "+91 98905 55667",
            email = "service@sheetalrefrig.com",
            website = "www.sheetalrefrigeration.co.in",
            rating = 4.5f,
            reviewsCount = 142,
            description = "Your trusted partner for large-scale refrigeration. Specialized in designing, installing, and repairing industrial cold storages, central air conditioning plants, water chillers, and milk cooling tanks. Also offers expert on-site repair for premium commercial deep freezers.",
            isFeatured = true,
            accentColorHex = "#4CAF50"
        ),
        // 5. Quality Housekeeping
        Business(
            id = "biz_qualityhk",
            name = "Quality Housekeeping",
            category = "Professional Services",
            address = "Suite 302, Mount Road, Sadar, Nagpur, 440001",
            phone = "+91 99230 88990",
            email = "contact@qualityhousekeeping.com",
            website = "www.qualityhousekeeping.in",
            rating = 4.7f,
            reviewsCount = 210,
            description = "ISO 9001:2015 certified facilities management and commercial cleaning company. Providing fully trained janitorial staff, deep sanitization, eco-friendly waste disposal, corporate facade washing, and complete pest control solutions for commercial high-rises and IT parks.",
            isFeatured = true,
            accentColorHex = "#E91E63"
        ),
        // 6. Furnisys Office Creations
        Business(
            id = "biz_furnisys",
            name = "Furnisys Office Creations",
            category = "Office Furniture & Interiors",
            address = "Opp. Telephone Exchange, CA Road, Nagpur, 440008",
            phone = "+91 98224 22334",
            email = "sales@furnisys.com",
            website = "www.furnisys.com",
            rating = 4.9f,
            reviewsCount = 76,
            description = "Nagpur's premium manufacturer of contemporary modular office workstations, executive desks, mesh back ergonomic chairs, acoustic partition screens, and custom board room furniture. Renowned for space-maximizing layouts and outstanding product warranty.",
            isFeatured = true,
            accentColorHex = "#FFEB3B"
        ),
        // 7. Nagpur IT Solutions
        Business(
            id = "biz_nagpurit",
            name = "Nagpur IT Solutions",
            category = "IT & Digital Services",
            address = "Gayatri Nagar, IT Park, Nagpur, 440022",
            phone = "+91 90110 56789",
            email = "info@nagpurit.com",
            website = "www.nagpurit.com",
            rating = 4.8f,
            reviewsCount = 112,
            description = "Top-rated IT support company offering high-speed enterprise software development, modern UI/UX design, mobile apps, secure cloud migration, and managed cyber security services. We deliver state-of-the-art technological architectures for Nagpur businesses.",
            isFeatured = false,
            accentColorHex = "#00E5FF"
        ),
        // 8. Orange City Coaching Academy
        Business(
            id = "biz_coaching",
            name = "Orange City Coaching Academy",
            category = "Education & Academy",
            address = "Abhyankar Nagar Road, Ramdaspeth, Nagpur, 440012",
            phone = "+91 71225 99001",
            email = "admissions@orangecityacademy.com",
            website = "www.orangecityacademy.com",
            rating = 4.7f,
            reviewsCount = 315,
            description = "The premier institute in Nagpur for competitive engineering (JEE Main/Advanced) and medical (NEET) entrance preparations. Featuring experienced PhD faculties, detailed test series analysis, offline simulation centers, and exceptional success records for over 15 years.",
            isFeatured = false,
            accentColorHex = "#9C27B0"
        ),
        // 9. Orange City Superspeciality Hospital
        Business(
            id = "biz_hospital",
            name = "Orange City Superspeciality Hospital",
            category = "Healthcare & Medical",
            address = "Somalwada, Wardha Road, Nagpur, 440015",
            phone = "+91 71222 55661",
            email = "care@ochri.com",
            website = "www.ochri.com",
            rating = 4.6f,
            reviewsCount = 422,
            description = "A standard-bearer of healthcare in Central India, providing 150+ beds, 24/7 advanced emergency trauma center, highly sterilized modular ICUs, and comprehensive multi-disciplinary surgical expertise (cardiology, neurology, orthopedics, oncology).",
            isFeatured = false,
            accentColorHex = "#00E676"
        ),
        // 10. Vidarbha Buildcon
        Business(
            id = "biz_buildcon",
            name = "Vidarbha Buildcon",
            category = "Real Estate & Builders",
            address = "Temple Road, Civil Lines, Nagpur, 440001",
            phone = "+91 98220 54321",
            email = "projects@vidarbhabuildcon.com",
            website = "www.vidarbhabuildcon.com",
            rating = 4.8f,
            reviewsCount = 82,
            description = "Vidarbha Buildcon is an award-winning real estate developer committed to constructing luxury apartments, high-end commercial spaces, and eco-friendly gated row-house communities with ultra-modern architectural elements and lush landscaping.",
            isFeatured = false,
            accentColorHex = "#FF9800"
        ),
        // 11. Grand Nagpur Caterers & Events
        Business(
            id = "biz_caterers",
            name = "Grand Nagpur Caterers & Events",
            category = "Event Management & Catering",
            address = "Lande Layout, Kamptee Road, Nagpur, 440026",
            phone = "+91 93700 11223",
            email = "grandnagpurcaterers@gmail.com",
            website = "www.grandnagpurcaterers.com",
            rating = 4.9f,
            reviewsCount = 175,
            description = "Turnkey premium wedding and corporate event management planners. Providing world-class thematic decor setups, high-fidelity lighting/sound solutions, and gourmet multi-cuisine catering featuring signature Nagpur Saoji fusion banquets.",
            isFeatured = false,
            accentColorHex = "#F44336"
        ),
        // 12. Nagpur Auto Tech & Garage
        Business(
            id = "biz_autotech",
            name = "Nagpur Auto Tech & Garage",
            category = "Automobiles & Garage",
            address = "Sector 2, MIDC Hingna, Nagpur, 440016",
            phone = "+91 95521 99008",
            email = "service@nagpurautotech.com",
            website = "www.nagpurautotech.com",
            rating = 4.7f,
            reviewsCount = 95,
            description = "A futuristic independent multi-brand car diagnostic workshop. Equipped with computerized scanner tools, advanced wheel alignment mechanics, premium paint booths, and specialized tuning experts for luxury European and performance vehicles.",
            isFeatured = false,
            accentColorHex = "#3F51B5"
        )
    )

    // Sanjay Chanekar Services List
    val marketingServices = listOf(
        MarketingService(
            id = "serv_smm",
            title = "Social Media Marketing (SMM)",
            price = "₹5,000",
            duration = "month",
            description = "Boost your local brand visibility on major networks like Facebook, Instagram, and local WhatsApp directories.",
            features = listOf(
                "15 Creative graphic & video posts",
                "Advanced localized audience targeting",
                "Interactive customer engagement & comments response",
                "VidarbhaDS featured banner highlight",
                "Monthly growth analytics & review"
            ),
            iconName = "share"
        ),
        MarketingService(
            id = "serv_gads",
            title = "Google Ads & PPC Campaigns",
            price = "₹8,000",
            duration = "setup & optimize",
            description = "Surgical search and map ads targeting high-intent local buyers actively seeking your services in Nagpur.",
            features = listOf(
                "Comprehensive local keyword harvesting",
                "Google Search & Maps location pin ranking",
                "Negative keywords exclusion to save cost",
                "Dynamic ad copywriting & Extensions integration",
                "Robust lead-form generation setup"
            ),
            iconName = "campaign"
        ),
        MarketingService(
            id = "serv_seo",
            title = "Local SEO & Business Listings",
            price = "₹3,000",
            duration = "one-time",
            description = "Build a strong search foundation. Sync Google Business Profile, Apple Maps, and local search networks.",
            features = listOf(
                "Google Business Profile (GMB) optimization",
                "VidarbhaDS premium tier profile listing",
                "Geo-tagged schema tags for local SEO",
                "Nap-consistent citation building across 50+ sites",
                "Review acquisition framework coaching"
            ),
            iconName = "travel_explore"
        ),
        MarketingService(
            id = "serv_analytics",
            title = "GA4 Web Analytics Setup",
            price = "₹4,000",
            duration = "setup",
            description = "Unlock clean attribution. See exactly which marketing platforms are bringing the highest ROI to your site.",
            features = listOf(
                "Google Analytics 4 custom property creation",
                "Conversion action & phone click tracking",
                "UTM link generator setup for tracking",
                "Visual reporting dashboard configured",
                "Cookie consent advice & configuration"
            ),
            iconName = "bar_chart"
        )
    )

    // Sanjay Chanekar Certifications
    val certifications = listOf(
        GoogleCertification(
            id = "cert_ads",
            title = "Google Search Ads Certification",
            issuer = "Google Skillshop",
            year = "2025",
            certUrl = "Certificate ID: 198273645 - Local Search Ads Expert",
            iconName = "workspace_premium"
        ),
        GoogleCertification(
            id = "cert_analytics",
            title = "Google Analytics Individual Qualification",
            issuer = "Google Skillshop",
            year = "2025",
            certUrl = "Certificate ID: GAIQ-9874112 - Web Traffic & Attribution",
            iconName = "verified_user"
        )
    )

    // Filter Logic
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun getFilteredBusinesses(): List<Business> {
        val query = _searchQuery.value.trim().lowercase()
        val cat = _selectedCategory.value

        return allBusinesses.filter { biz ->
            val matchesCategory = (cat == null || biz.category == cat)
            val matchesSearch = (query.isEmpty() || 
                    biz.name.lowercase().contains(query) ||
                    biz.description.lowercase().contains(query) ||
                    biz.category.lowercase().contains(query) ||
                    biz.address.lowercase().contains(query))
            matchesCategory && matchesSearch
        }
    }

    // App Setting mutations
    fun updateTheme(theme: AppTheme) {
        _settingsState.update { it.copy(selectedTheme = theme) }
    }

    fun toggleDarkMode(enabled: Boolean) {
        _settingsState.update { it.copy(isDarkMode = enabled) }
    }

    fun updateFontSize(size: FontSize) {
        _settingsState.update { it.copy(fontSize = size) }
    }

    fun updateAnimationSpeed(speed: Float) {
        _settingsState.update { it.copy(animationSpeedMultiplier = speed) }
    }

    fun toggleNotifications(enabled: Boolean) {
        _settingsState.update { it.copy(isNotificationEnabled = enabled) }
    }

    fun toggleMarketingTips(enabled: Boolean) {
        _settingsState.update { it.copy(isMarketingTipsEnabled = enabled) }
    }

    fun updateSearchRadius(radius: Int) {
        _settingsState.update { it.copy(searchRadiusKm = radius) }
    }

    fun toggleAutoSuggestions(enabled: Boolean) {
        _settingsState.update { it.copy(autoSuggestionsEnabled = enabled) }
    }

    fun toggleShareAnalytics(enabled: Boolean) {
        _settingsState.update { it.copy(shareAnalytics = enabled) }
    }

    fun togglePersonalization(enabled: Boolean) {
        _settingsState.update { it.copy(personalizationEnabled = enabled) }
    }
}
