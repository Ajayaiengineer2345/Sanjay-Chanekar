import React, { useState, useMemo } from 'react';
import { 
  Home, 
  Search, 
  Megaphone, 
  User, 
  Settings, 
  Phone, 
  Mail, 
  MapPin, 
  Info, 
  ExternalLink, 
  Palette, 
  Layers, 
  Sparkles, 
  Award, 
  Globe, 
  CheckCircle, 
  Trash2, 
  ShieldCheck, 
  Lock, 
  Bell, 
  Map, 
  ThumbsUp, 
  ArrowRight,
  TrendingUp,
  Cpu,
  Bookmark,
  Share2
} from 'lucide-react';

// Mock Directory Listings for Nagpur
const initialListings = [
  {
    id: 1,
    name: "Nagpur Engineering Works",
    category: "Engineering & Industrial",
    phone: "+91 712 254 8899",
    email: "info@nagpurengg.com",
    address: "Plot 42, MIDC Hingna Industrial Area, Nagpur - 440028",
    rating: 4.8,
    reviews: 124,
    status: "Verified",
    smmTier: "Premium Partner",
    desc: "Manufacturer of high-precision heavy engineering gears, lathed components, and industrial CNC machinery components with ISO 9001 certification."
  },
  {
    id: 2,
    name: "Haldiram's Sweets & Restaurant",
    category: "Restaurants & Bakeries",
    phone: "+91 98224 55660",
    email: "care@haldiramsnagpur.com",
    address: "Anjuman Complex, Sadar, Nagpur - 440001",
    rating: 4.9,
    reviews: 2450,
    status: "Verified",
    smmTier: "Gold Listing",
    desc: "Nagpur's signature sweet destination. Famous for Nagpur orange burfee, premium dry fruits, multi-cuisine family restaurant dining, and quick bites."
  },
  {
    id: 3,
    name: "Crescent Hospital & Heart Centre",
    category: "Hospital & Medical Services",
    phone: "+91 712 272 5412",
    email: "emergency@crescentheart.org",
    address: "Behind Lokmat Square, Dhantoli, Nagpur - 440012",
    rating: 4.7,
    reviews: 480,
    status: "Verified",
    smmTier: "Premium Partner",
    desc: "Super-specialty cardiac hospital and critical care unit providing round-the-clock emergency medical attention with advanced trauma systems."
  },
  {
    id: 4,
    name: "Dharampeth Science College",
    category: "Educational Institutions",
    phone: "+91 712 224 1372",
    email: "admission@dharampethscience.edu.in",
    address: "North Ambazari Road, Near Shankar Nagar Square, Nagpur - 440010",
    rating: 4.6,
    reviews: 190,
    status: "Verified",
    smmTier: "Basic Verified",
    desc: "Premier government-aided science institution in Central India providing world-class laboratory education, research fields, and career guidance."
  },
  {
    id: 5,
    name: "Kapse & Associates Law Firm",
    category: "Professional Consultancies",
    phone: "+91 98901 22334",
    email: "contact@kapselaw.in",
    address: "204, High Court Road, Civil Lines, Nagpur - 440001",
    rating: 4.9,
    reviews: 65,
    status: "Verified",
    smmTier: "Gold Listing",
    desc: "Top corporate and civil litigation chambers offering regulatory compliance, real estate title searching, and direct arbitration support."
  },
  {
    id: 6,
    name: "Orange City Travels & Logistics",
    category: "Travel & Automotive",
    phone: "+91 93700 11223",
    email: "bookings@orangecitytravels.com",
    address: "Ganeshpeth Bus Stand Road, Nagpur - 440018",
    rating: 4.5,
    reviews: 820,
    status: "Verified",
    smmTier: "Basic Verified",
    desc: "Intercity sleeper bus and parcel logistics service provider operating nightly lines to Pune, Mumbai, Indore, Raipur, and Hyderabad."
  },
  {
    id: 7,
    name: "Shriram Steel Rolling Mills",
    category: "Engineering & Industrial",
    phone: "+91 712 264 0122",
    email: "sales@shriramrolling.com",
    address: "Plot B-12, Kamptee Road Industrial Area, Nagpur - 440026",
    rating: 4.7,
    reviews: 89,
    status: "Verified",
    smmTier: "Premium Partner",
    desc: "Leading fabricator of structural steel, construction TMT bars, carbon alloy angles, and customized heavy beams for infrastructure setups."
  },
  {
    id: 8,
    name: "Veeraswami South Indian Cafe",
    category: "Restaurants & Bakeries",
    phone: "+91 712 252 4432",
    email: "veeraswami.ngp@gmail.com",
    address: "Mount Road, Sadar, Nagpur - 440001",
    rating: 4.8,
    reviews: 940,
    status: "Verified",
    smmTier: "Gold Listing",
    desc: "Nagpur's oldest authentic South Indian culinary venue serving original filter coffee, ghee roast paper dosas, and steamed idlis since 1956."
  },
  {
    id: 9,
    name: "Wockhardt Super Specialty Hospital",
    category: "Hospital & Medical Services",
    phone: "+91 712 662 4100",
    email: "nagpur@wockhardthospitals.com",
    address: "1643, North Ambazari Road, Shankar Nagar, Nagpur - 440010",
    rating: 4.7,
    reviews: 1420,
    status: "Verified",
    smmTier: "Premium Partner",
    desc: "Comprehensive diagnostic and surgical healthcare network with advanced neurosurgery, oncology, nephrology, and neonatal care panels."
  }
];

// Themes definitions
const themes = {
  MIDNIGHT_NAVY: {
    primary: '#1E90FF',
    secondary: '#00FFFF',
    background: '#070B19',
    surface: '#0F1832',
    glowColor: 'rgba(0, 255, 255, 0.5)',
    displayName: 'Midnight Navy'
  },
  CYBERPUNK_NEON: {
    primary: '#FF00FF',
    secondary: '#39FF14',
    background: '#0D0212',
    surface: '#1A0426',
    glowColor: 'rgba(57, 255, 20, 0.5)',
    displayName: 'Neon Grid'
  },
  SOLAR_BLARE: {
    primary: '#FF4500',
    secondary: '#FFD700',
    background: '#150A00',
    surface: '#2C1400',
    glowColor: 'rgba(255, 215, 0, 0.5)',
    displayName: 'Solar Blare'
  },
  COSMIC_VOID: {
    primary: '#8A2BE2',
    secondary: '#FF1493',
    background: '#05020A',
    surface: '#110521',
    glowColor: 'rgba(255, 20, 147, 0.5)',
    displayName: 'Cosmic Void'
  },
  SHADOW_ORCHID: {
    primary: '#9D00FF',
    secondary: '#00FFCC',
    background: '#090510',
    surface: '#140E22',
    glowColor: 'rgba(0, 255, 204, 0.5)',
    displayName: 'Shadow Orchid'
  }
};

export default function App() {
  const [activeTab, setActiveTab] = useState('home');
  const [themeKey, setThemeKey] = useState('MIDNIGHT_NAVY');
  const [fontSize, setFontSize] = useState('Normal'); // Small, Normal, Large
  const [isDarkMode, setIsDarkMode] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [listings, setListings] = useState(initialListings);
  const [bookmarkedListings, setBookmarkedListings] = useState([]);
  
  // Settings states
  const [notifsEnabled, setNotifsEnabled] = useState(true);
  const [marketingTips, setMarketingTips] = useState(true);
  const [searchRadius, setSearchRadius] = useState(15);
  const [shareDiagnostics, setShareDiagnostics] = useState(true);
  const [personalizedAds, setPersonalizedAds] = useState(true);

  const theme = themes[themeKey];

  // font sizing classes
  const fontScaleClass = () => {
    if (fontSize === 'Small') return 'text-sm';
    if (fontSize === 'Large') return 'text-lg';
    return 'text-base';
  };

  // Filter listings
  const filteredListings = useMemo(() => {
    return listings.filter(item => {
      const matchSearch = item.name.toLowerCase().includes(searchQuery.toLowerCase()) || 
                          item.desc.toLowerCase().includes(searchQuery.toLowerCase()) ||
                          item.address.toLowerCase().includes(searchQuery.toLowerCase());
      const matchCat = selectedCategory ? item.category === selectedCategory : true;
      return matchSearch && matchCat;
    });
  }, [listings, searchQuery, selectedCategory]);

  const categories = useMemo(() => {
    return Array.from(new Set(listings.map(l => l.category)));
  }, [listings]);

  const handleToggleBookmark = (id) => {
    if (bookmarkedListings.includes(id)) {
      setBookmarkedListings(prev => prev.filter(item => item !== id));
    } else {
      setBookmarkedListings(prev => [...prev, id]);
    }
  };

  const handleClearCache = () => {
    alert("Local browser yellow pages search cache has been successfully flushed!");
  };

  return (
    <div 
      className="min-h-screen relative flex flex-col transition-colors duration-300"
      style={{ 
        backgroundColor: isDarkMode ? theme.background : '#F8FAFC',
        color: isDarkMode ? '#FFFFFF' : '#0F172A'
      }}
    >
      {/* Background Cyber Grid */}
      {isDarkMode && <div className="absolute inset-0 cyber-grid opacity-30 pointer-events-none" />}
      
      {/* Glowing Ambient Radial Circle */}
      {isDarkMode && (
        <div 
          className="absolute top-0 left-1/2 -translate-x-1/2 w-[600px] h-[300px] rounded-full blur-[120px] pointer-events-none opacity-20 transition-all duration-700"
          style={{ 
            background: `radial-gradient(circle, ${theme.primary} 0%, ${theme.secondary} 100%)` 
          }}
        />
      )}

      {/* Header Bar */}
      <header 
        className="sticky top-0 z-40 backdrop-blur-md border-b transition-colors duration-300"
        style={{ 
          backgroundColor: isDarkMode ? `${theme.surface}95` : '#FFFFFF95',
          borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
        }}
      >
        <div className="max-w-6xl mx-auto px-4 py-3 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div 
              className="w-10 h-10 rounded-xl flex items-center justify-center font-bold text-xl shadow-lg"
              style={{ 
                background: `linear-gradient(135deg, ${theme.primary}, ${theme.secondary})`,
                boxShadow: `0 0 15px ${theme.glowColor}`
              }}
            >
              ⚡
            </div>
            <div>
              <h1 className="font-extrabold text-lg tracking-tight flex items-center gap-1.5">
                VidarbhaDS <span className="text-xs px-2 py-0.5 rounded-full font-bold uppercase tracking-wider text-black bg-cyan-400">Live</span>
              </h1>
              <p className="text-[10px] text-gray-400 font-medium">Official Nagpur Yellow Pages Web Companion</p>
            </div>
          </div>

          {/* Desktop Nav Tabs */}
          <nav className="hidden md:flex items-center space-x-1 bg-opacity-30 rounded-lg p-1" style={{ backgroundColor: isDarkMode ? '#070B19' : '#F1F5F9' }}>
            {[
              { id: 'home', label: 'Home', icon: Home },
              { id: 'directory', label: 'Directory', icon: Search },
              { id: 'services', label: 'Services', icon: Megaphone },
              { id: 'about', label: 'About', icon: User },
              { id: 'settings', label: 'Settings', icon: Settings },
            ].map(tab => {
              const Icon = tab.icon;
              const isActive = activeTab === tab.id;
              return (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`flex items-center space-x-1.5 px-3 py-1.5 rounded-md text-xs font-bold transition-all duration-200 ${
                    isActive 
                      ? 'text-white' 
                      : 'text-gray-400 hover:text-white'
                  }`}
                  style={isActive ? { 
                    backgroundColor: theme.primary,
                    boxShadow: `0 0 10px ${theme.glowColor}40`
                  } : {}}
                >
                  <Icon size={14} />
                  <span>{tab.label}</span>
                </button>
              );
            })}
          </nav>
          
          <div className="flex items-center space-x-2">
            <span className="hidden lg:inline-block text-xs text-gray-400 font-mono">App Version 1.0.0</span>
            <a 
              href="https://vercel.com" 
              target="_blank" 
              rel="noreferrer"
              className="text-[11px] font-bold bg-white text-black px-3 py-1.5 rounded-md hover:bg-neutral-200 flex items-center space-x-1 transition-all"
            >
              <span>Vercel Build</span>
              <ExternalLink size={12} />
            </a>
          </div>
        </div>
      </header>

      {/* Main Content Pane */}
      <main className={`flex-grow max-w-6xl mx-auto px-4 py-6 w-full ${fontScaleClass()}`}>
        
        {/* VIEW 1: HOME */}
        {activeTab === 'home' && (
          <div className="space-y-8 animate-fade-in">
            {/* Banner Section */}
            <div 
              className="relative rounded-2xl p-6 md:p-10 border overflow-hidden"
              style={{ 
                backgroundColor: isDarkMode ? `${theme.surface}60` : '#F1F5F9',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="relative z-10 max-w-2xl">
                <span className="inline-flex items-center space-x-1 text-xs font-bold px-2.5 py-1 rounded-full mb-4" style={{ backgroundColor: `${theme.primary}20`, color: theme.secondary }}>
                  <Sparkles size={12} />
                  <span>9+ Years Digital Domination in Nagpur</span>
                </span>
                <h2 className="text-3xl md:text-4xl font-extrabold tracking-tight leading-tight">
                  Democratizing Local Search & <span className="text-transparent bg-clip-text bg-gradient-to-r" style={{ backgroundImage: `linear-gradient(to right, ${theme.primary}, ${theme.secondary})` }}>Business Connectivity</span>
                </h2>
                <p className="mt-3 text-sm text-gray-400 leading-relaxed">
                  Vidarbhads.com provides residents with lightning-fast offline-first business discovery and direct communication pipelines. Cataloging verified manufacturing hubs, clinical chambers, and custom services across Nagpur.
                </p>
                
                {/* Embedded quick search */}
                <div className="mt-6 flex flex-col sm:flex-row gap-2">
                  <div className="relative flex-grow">
                    <Search className="absolute left-3.5 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
                    <input 
                      type="text" 
                      placeholder="Search 900+ verified businesses in Shankar Nagar, Sadar, MIDC..."
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                      className="w-full pl-10 pr-4 py-2.5 rounded-xl border text-xs focus:outline-none transition-all duration-200"
                      style={{ 
                        backgroundColor: isDarkMode ? '#070B19' : '#FFFFFF',
                        borderColor: isDarkMode ? '#1E293B' : '#CBD5E1',
                        color: isDarkMode ? '#FFFFFF' : '#0F172A'
                      }}
                    />
                  </div>
                  <button 
                    onClick={() => setActiveTab('directory')}
                    className="px-5 py-2.5 rounded-xl font-bold text-xs text-white transition-all duration-200 hover:brightness-110 flex items-center justify-center space-x-1.5"
                    style={{ backgroundColor: theme.primary }}
                  >
                    <span>Search Yellow Pages</span>
                    <ArrowRight size={14} />
                  </button>
                </div>
              </div>
            </div>

            {/* Grid stats */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
              {[
                { label: "Active Listings", value: "900+", color: theme.primary },
                { label: "Nagpur Sectors", value: "12+", color: theme.secondary },
                { label: "Years of Trust", value: "9 Years", color: theme.primary },
                { label: "Audited Daily", value: "100%", color: theme.secondary },
              ].map((stat, i) => (
                <div 
                  key={i} 
                  className="p-4 rounded-xl border text-center transition-all duration-300"
                  style={{ 
                    backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                    borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
                  }}
                >
                  <p className="text-2xl font-black" style={{ color: stat.color }}>{stat.value}</p>
                  <p className="text-[11px] text-gray-400 uppercase tracking-widest mt-1 font-bold">{stat.label}</p>
                </div>
              ))}
            </div>

            {/* Quick Browse Categories */}
            <div>
              <h3 className="text-sm font-extrabold uppercase tracking-widest text-gray-400 mb-4">Browse Nagpur Districts</h3>
              <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-6 gap-3">
                {categories.map((cat, idx) => (
                  <button
                    key={idx}
                    onClick={() => {
                      setSelectedCategory(cat);
                      setActiveTab('directory');
                    }}
                    className="p-3 rounded-xl border text-left text-xs font-bold transition-all hover:-translate-y-0.5"
                    style={{ 
                      backgroundColor: isDarkMode ? `${theme.surface}40` : '#F8FAFC',
                      borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
                    }}
                  >
                    <span className="block text-[10px] text-gray-400 mb-1">Category</span>
                    <span className="line-clamp-1">{cat}</span>
                  </button>
                ))}
              </div>
            </div>

            {/* Spotlight Listings */}
            <div>
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-sm font-extrabold uppercase tracking-widest text-gray-400">SMM Verified Partnerships</h3>
                <button 
                  onClick={() => setActiveTab('directory')}
                  className="text-xs font-bold flex items-center space-x-1"
                  style={{ color: theme.secondary }}
                >
                  <span>View All Listings</span>
                  <ArrowRight size={12} />
                </button>
              </div>
              <div className="grid md:grid-cols-3 gap-4">
                {listings.slice(0, 3).map(item => (
                  <div 
                    key={item.id}
                    className="p-5 rounded-xl border flex flex-col justify-between transition-all duration-300 hover:shadow-lg"
                    style={{ 
                      backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                      borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
                    }}
                  >
                    <div>
                      <div className="flex justify-between items-start mb-2">
                        <span className="text-[9px] px-2 py-0.5 rounded-full font-bold tracking-wider uppercase bg-yellow-400 text-black">
                          {item.smmTier}
                        </span>
                        <div className="flex items-center space-x-1 text-xs font-bold">
                          <span>⭐</span>
                          <span>{item.rating}</span>
                        </div>
                      </div>
                      <h4 className="font-bold text-sm mb-1">{item.name}</h4>
                      <p className="text-xs text-gray-400 line-clamp-2 leading-relaxed">{item.desc}</p>
                    </div>
                    
                    <div className="mt-4 pt-3 border-t flex items-center justify-between" style={{ borderColor: isDarkMode ? '#1E293B' : '#F1F5F9' }}>
                      <span className="text-[11px] text-gray-400 font-mono">{item.category}</span>
                      <a href={`tel:${item.phone}`} className="p-1.5 rounded-lg text-black bg-cyan-400 hover:bg-cyan-500 transition-colors">
                        <Phone size={12} />
                      </a>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {/* VIEW 2: DIRECTORY */}
        {activeTab === 'directory' && (
          <div className="space-y-6 animate-fade-in">
            {/* Search Filters header */}
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div>
                <h2 className="text-xl font-black tracking-tight">Active Nagpur Directories</h2>
                <p className="text-xs text-gray-400">Search and filter verified service providers in Nagpur</p>
              </div>

              {/* Category Dropdown */}
              <select
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
                className="px-3 py-2 text-xs font-bold rounded-xl border focus:outline-none"
                style={{ 
                  backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                  borderColor: isDarkMode ? '#1E293B' : '#CBD5E1',
                  color: isDarkMode ? '#FFFFFF' : '#0F172A'
                }}
              >
                <option value="">All Categories</option>
                {categories.map((cat, i) => (
                  <option key={i} value={cat}>{cat}</option>
                ))}
              </select>
            </div>

            {/* Search Input bar */}
            <div className="relative">
              <Search className="absolute left-3.5 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
              <input 
                type="text" 
                placeholder="Search by company name, description details, keyword..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-3 rounded-xl border text-xs focus:outline-none"
                style={{ 
                  backgroundColor: isDarkMode ? `${theme.surface}50` : '#FFFFFF',
                  borderColor: isDarkMode ? '#1E293B' : '#CBD5E1',
                  color: isDarkMode ? '#FFFFFF' : '#0F172A'
                }}
              />
            </div>

            {/* Categories filter badge row */}
            <div className="flex flex-wrap gap-1.5 pb-2">
              <button
                onClick={() => setSelectedCategory('')}
                className={`px-3 py-1.5 rounded-full text-[10px] font-extrabold uppercase tracking-wider transition-all duration-200 ${
                  selectedCategory === '' 
                    ? 'text-black bg-cyan-400' 
                    : 'text-gray-400 bg-[#1E293B] hover:text-white'
                }`}
              >
                All Categories
              </button>
              {categories.map((cat, i) => {
                const isSelected = selectedCategory === cat;
                return (
                  <button
                    key={i}
                    onClick={() => setSelectedCategory(cat)}
                    className={`px-3 py-1.5 rounded-full text-[10px] font-extrabold uppercase tracking-wider transition-all duration-200 ${
                      isSelected 
                        ? 'text-black bg-cyan-400' 
                        : 'text-gray-400 bg-[#1E293B] hover:text-white'
                    }`}
                  >
                    {cat}
                  </button>
                );
              })}
            </div>

            {/* Results Listings */}
            <div className="space-y-4">
              {filteredListings.length > 0 ? (
                filteredListings.map(item => (
                  <div 
                    key={item.id}
                    className="p-5 rounded-xl border transition-all duration-300 hover:shadow-lg flex flex-col md:flex-row justify-between gap-4"
                    style={{ 
                      backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                      borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
                    }}
                  >
                    <div className="space-y-2 max-w-3xl">
                      <div className="flex flex-wrap items-center gap-2">
                        <span className="text-[9px] font-extrabold uppercase px-2.5 py-0.5 rounded-full text-black bg-gradient-to-r" style={{ backgroundImage: `linear-gradient(to right, ${theme.primary}, ${theme.secondary})` }}>
                          {item.smmTier}
                        </span>
                        <span className="text-[9px] font-bold text-emerald-400 flex items-center gap-1">
                          <CheckCircle size={10} /> Verified Listing
                        </span>
                      </div>
                      <h3 className="text-base font-black">{item.name}</h3>
                      <p className="text-xs text-gray-400 leading-relaxed">{item.desc}</p>
                      
                      <div className="flex flex-col sm:flex-row gap-3 pt-2 text-[11px] text-gray-400 font-mono">
                        <span className="flex items-center gap-1"><MapPin size={12} className="text-cyan-400" /> {item.address}</span>
                      </div>
                    </div>

                    <div className="flex flex-col sm:flex-row md:flex-col justify-end items-stretch gap-2 min-w-[140px]">
                      <a 
                        href={`tel:${item.phone}`}
                        className="py-2 px-3 rounded-lg text-xs font-bold text-center text-white bg-[#1E293B] hover:bg-[#2D3E55] transition-all flex items-center justify-center gap-2"
                      >
                        <Phone size={12} />
                        <span>Call Business</span>
                      </a>
                      <a 
                        href={`mailto:${item.email}`}
                        className="py-2 px-3 rounded-lg text-xs font-bold text-center text-black bg-cyan-400 hover:bg-cyan-500 transition-all flex items-center justify-center gap-2"
                      >
                        <Mail size={12} />
                        <span>Email Partner</span>
                      </a>
                    </div>
                  </div>
                ))
              ) : (
                <div className="p-8 text-center rounded-xl border" style={{ borderColor: isDarkMode ? '#1E293B' : '#CBD5E1' }}>
                  <p className="text-xs text-gray-400">No active yellow pages directory listings found matching criteria.</p>
                </div>
              )}
            </div>
          </div>
        )}

        {/* VIEW 3: SERVICES */}
        {activeTab === 'services' && (
          <div className="space-y-6 animate-fade-in">
            <div className="text-center max-w-xl mx-auto space-y-2">
              <span className="inline-flex items-center space-x-1 text-xs font-bold px-2.5 py-1 rounded-full text-black bg-cyan-400">
                <Megaphone size={12} />
                <span>Premium SMM Services</span>
              </span>
              <h2 className="text-2xl font-black">VidarbhaDS Growth Packages</h2>
              <p className="text-xs text-gray-400">Dominate Nagpur local search rankings with verified Google Ads, SEO listing audits, and social media marketing blueprints curated by Sanjay Chanekar.</p>
            </div>

            <div className="grid md:grid-cols-3 gap-6 pt-4">
              {[
                {
                  title: "Local Business Booster",
                  price: "₹5,000 / mo",
                  color: "#1E90FF",
                  features: [
                    "Guaranteed Yellow Pages Verification",
                    "Custom SEO-Optimized Business Profile",
                    "Local Nagpur Google Maps optimization",
                    "1 Monthly SEO listing audit",
                    "Email promotion integration"
                  ],
                  highlight: false
                },
                {
                  title: "SMM Hyper-Growth Pro",
                  price: "₹12,000 / mo",
                  color: "#FF00FF",
                  features: [
                    "Dual-Platform Social Media Strategy",
                    "High-Click Google Ads Campaign setup",
                    "3 Custom social graphics per week",
                    "Sanjay Chanekar personal 1-on-1 audit",
                    "Verified Golden Yellow Pages ranking",
                    "Dynamic analytics performance dashboard"
                  ],
                  highlight: true
                },
                {
                  title: "Industrial Enterprise Domination",
                  price: "Custom Quote",
                  color: "#00FFFF",
                  features: [
                    "Multi-Region Vidarbha SEO footprint",
                    "Unlimited Yellow Pages premium listings",
                    "B2B Lead Generation funnel setup",
                    "White-glove CRM & WhatsApp API integration",
                    "Weekly video marketing outlines",
                    "24/7 dedicated local strategist support"
                  ],
                  highlight: false
                }
              ].map((pkg, i) => (
                <div 
                  key={i}
                  className={`p-6 rounded-2xl border flex flex-col justify-between transition-all duration-300 hover:scale-[1.02] ${
                    pkg.highlight ? 'ring-2 ring-cyan-400 relative' : ''
                  }`}
                  style={{ 
                    backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                    borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
                  }}
                >
                  {pkg.highlight && (
                    <span className="absolute -top-3 left-1/2 -translate-x-1/2 bg-cyan-400 text-black text-[10px] font-black uppercase tracking-widest px-3 py-1 rounded-full">
                      Most Popular Plan
                    </span>
                  )}
                  
                  <div className="space-y-4">
                    <h3 className="font-extrabold text-base tracking-tight">{pkg.title}</h3>
                    <p className="text-2xl font-black" style={{ color: pkg.color }}>{pkg.price}</p>
                    <hr style={{ borderColor: isDarkMode ? '#1E293B' : '#E2E8F0' }} />
                    <ul className="space-y-2.5 text-xs text-gray-400">
                      {pkg.features.map((feat, fIdx) => (
                        <li key={fIdx} className="flex items-start gap-2">
                          <span className="text-emerald-400 font-bold mt-0.5">✓</span>
                          <span>{feat}</span>
                        </li>
                      ))}
                    </ul>
                  </div>

                  <button 
                    onClick={() => {
                      alert(`Thank you for interest in ${pkg.title}! Sanjay Chanekar will contact you shortly.`);
                    }}
                    className="mt-6 py-2.5 w-full rounded-xl text-xs font-bold transition-all"
                    style={{ 
                      backgroundColor: pkg.highlight ? theme.primary : '#1E293B',
                      color: '#FFFFFF'
                    }}
                  >
                    Get Enrolled Now
                  </button>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* VIEW 4: ABOUT */}
        {activeTab === 'about' && (
          <div className="space-y-6 max-w-2xl mx-auto animate-fade-in">
            {/* Biography Card */}
            <div 
              className="p-6 md:p-8 rounded-2xl border text-center space-y-4"
              style={{ 
                backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              {/* Profile Avatar Frame */}
              <div className="relative w-24 h-24 mx-auto">
                <div 
                  className="absolute inset-0 rounded-full blur-md"
                  style={{ background: `linear-gradient(135deg, ${theme.primary}, ${theme.secondary})` }}
                />
                <div className="relative w-24 h-24 rounded-full bg-slate-800 border-2 border-cyan-400 overflow-hidden flex items-center justify-center text-4xl">
                  👨‍💻
                </div>
              </div>

              <div>
                <h2 className="text-xl font-black">Sanjay Chanekar</h2>
                <p className="text-xs font-bold" style={{ color: theme.secondary }}>Founder, VidarbhaDS & SMM Strategist</p>
              </div>

              <p className="text-xs text-gray-400 leading-relaxed max-w-lg mx-auto">
                Providing offline businesses a solid digital footprint for over 9 years in Nagpur. Specialist in Social Media Marketing (SMM), Google Ads search networks, and organic Google Business profile ranking.
              </p>

              <div className="flex flex-col sm:flex-row justify-center gap-2 max-w-sm mx-auto pt-2">
                <a 
                  href="tel:+919823011223"
                  className="py-2 px-4 rounded-lg bg-cyan-400 text-black text-xs font-bold flex items-center justify-center gap-2 transition-colors hover:bg-cyan-500"
                >
                  <Phone size={14} />
                  <span>Call +91 98230 11223</span>
                </a>
                <a 
                  href="mailto:sanjaychanekar@vidarbhads.com"
                  className="py-2 px-4 rounded-lg bg-[#1E293B] hover:bg-[#2D3E55] text-white text-xs font-bold flex items-center justify-center gap-2 transition-colors"
                >
                  <Mail size={14} />
                  <span>Email Sanjay</span>
                </a>
              </div>
            </div>

            {/* Office Coordinates */}
            <div 
              className="p-6 rounded-2xl border space-y-4"
              style={{ 
                backgroundColor: isDarkMode ? `${theme.surface}50` : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="flex items-center space-x-2">
                <MapPin className="text-cyan-400" size={20} />
                <h3 className="font-bold text-sm">Registered Headquarters</h3>
              </div>
              <p className="text-xs text-gray-400 leading-relaxed font-mono">
                Vidarbha Directory Services<br />
                Plot 14, Dharampeth Extension,<br />
                Near Shankar Nagar Square, Nagpur, Maharashtra - 440010
              </p>
              <a 
                href="https://maps.google.com/?q=Shankar+Nagar+Nagpur"
                target="_blank"
                rel="noreferrer"
                className="inline-flex items-center space-x-1 text-xs font-bold text-cyan-400 hover:underline"
              >
                <span>Navigate on Google Maps</span>
                <ExternalLink size={12} />
              </a>
            </div>
          </div>
        )}

        {/* VIEW 5: SETTINGS */}
        {activeTab === 'settings' && (
          <div className="space-y-6 max-w-2xl mx-auto animate-fade-in">
            <div className="flex items-center space-x-3 mb-2">
              <Settings className="text-cyan-400" size={24} />
              <div>
                <h2 className="text-xl font-black">Applet System Parameters</h2>
                <p className="text-xs text-gray-400">Configure theme indices, search ranges, and notification rules</p>
              </div>
            </div>

            {/* Theme picker */}
            <div 
              className="p-5 rounded-xl border space-y-3"
              style={{ 
                backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="flex items-center space-x-2">
                <Palette className="text-cyan-400" size={16} />
                <h3 className="text-xs font-extrabold uppercase tracking-widest text-gray-300">Aesthetic Neon Theme</h3>
              </div>
              
              <div className="grid grid-cols-2 sm:grid-cols-3 gap-2">
                {Object.keys(themes).map(key => (
                  <button
                    key={key}
                    onClick={() => setThemeKey(key)}
                    className={`p-3 rounded-lg border text-left text-xs font-bold transition-all ${
                      themeKey === key 
                        ? 'border-cyan-400 bg-cyan-400 bg-opacity-10' 
                        : 'border-[#1E293B] hover:border-gray-500'
                    }`}
                  >
                    <span className="block text-[10px] text-gray-400 font-mono mb-1">Index ID</span>
                    <span>{themes[key].displayName}</span>
                  </button>
                ))}
              </div>
            </div>

            {/* Notification and preference parameters */}
            <div 
              className="p-5 rounded-xl border space-y-4"
              style={{ 
                backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="flex items-center space-x-2">
                <Bell className="text-cyan-400" size={16} />
                <h3 className="text-xs font-extrabold uppercase tracking-widest text-gray-300">Notification Rules</h3>
              </div>

              <div className="space-y-3">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-xs font-bold">Listing Audit Verification Alerts</p>
                    <p className="text-[10px] text-gray-400">Notify when directory partner statuses are audited</p>
                  </div>
                  <input 
                    type="checkbox" 
                    checked={notifsEnabled} 
                    onChange={(e) => setNotifsEnabled(e.target.checked)}
                    className="w-4 h-4 rounded text-cyan-400 focus:ring-cyan-400"
                  />
                </div>

                <hr style={{ borderColor: isDarkMode ? '#1E293B' : '#E2E8F0' }} />

                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-xs font-bold">SMM Strategy Tips</p>
                    <p className="text-[10px] text-gray-400">Weekly advice from Sanjay Chanekar regarding Google Ads & SEO</p>
                  </div>
                  <input 
                    type="checkbox" 
                    checked={marketingTips} 
                    onChange={(e) => setMarketingTips(e.target.checked)}
                    className="w-4 h-4 rounded text-cyan-400 focus:ring-cyan-400"
                  />
                </div>
              </div>
            </div>

            {/* Search range selection */}
            <div 
              className="p-5 rounded-xl border space-y-3"
              style={{ 
                backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="flex items-center space-x-2">
                <Map className="text-cyan-400" size={16} />
                <h3 className="text-xs font-extrabold uppercase tracking-widest text-gray-300">Nagpur Search Boundary</h3>
              </div>
              <div className="space-y-2">
                <div className="flex justify-between text-xs">
                  <span>Local Search Radius</span>
                  <span className="font-bold text-cyan-400">{searchRadius} km</span>
                </div>
                <input 
                  type="range" 
                  min="1" 
                  max="50" 
                  value={searchRadius} 
                  onChange={(e) => setSearchRadius(Number(e.target.value))}
                  className="w-full accent-cyan-400"
                />
              </div>
            </div>

            {/* Privacy logs / Cache flush button */}
            <div 
              className="p-5 rounded-xl border space-y-4"
              style={{ 
                backgroundColor: isDarkMode ? theme.surface : '#FFFFFF',
                borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
              }}
            >
              <div className="flex items-center space-x-2">
                <Lock className="text-cyan-400" size={16} />
                <h3 className="text-xs font-extrabold uppercase tracking-widest text-gray-300">Privacy & Diagnostic Cache</h3>
              </div>

              <div className="flex justify-between items-center">
                <div>
                  <p className="text-xs font-bold">Flush Yellow Pages Directory Cache</p>
                  <p className="text-[10px] text-gray-400">Clear all local search queries and stored partner profiles</p>
                </div>
                <button 
                  onClick={handleClearCache}
                  className="px-3 py-1.5 bg-red-600 hover:bg-red-700 text-white rounded-lg text-[11px] font-bold flex items-center space-x-1.5 transition-all"
                >
                  <Trash2 size={12} />
                  <span>Flush Cache</span>
                </button>
              </div>
            </div>
          </div>
        )}

      </main>

      {/* Bottom Nav bar on mobile screens */}
      <footer 
        className="md:hidden sticky bottom-0 z-40 backdrop-blur-md border-t transition-all duration-300"
        style={{ 
          backgroundColor: isDarkMode ? `${theme.surface}DF` : '#FFFFFFDF',
          borderColor: isDarkMode ? '#1E293B' : '#E2E8F0'
        }}
      >
        <div className="grid grid-cols-5 h-16">
          {[
            { id: 'home', label: 'Home', icon: Home },
            { id: 'directory', label: 'Directory', icon: Search },
            { id: 'services', label: 'Services', icon: Megaphone },
            { id: 'about', label: 'About', icon: User },
            { id: 'settings', label: 'Settings', icon: Settings },
          ].map(tab => {
            const Icon = tab.icon;
            const isActive = activeTab === tab.id;
            return (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className="flex flex-col items-center justify-center space-y-1 transition-colors"
                style={{ color: isActive ? theme.primary : '#94A3B8' }}
              >
                <Icon size={18} />
                <span className="text-[9px] font-bold tracking-tight">{tab.label}</span>
              </button>
            );
          })}
        </div>
      </footer>
    </div>
  );
}
