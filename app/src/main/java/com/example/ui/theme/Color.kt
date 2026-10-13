package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================================================
// AAA CASUAL GAME THEME PALETTE (From Reference Image)
// Rich vibrant pill gradients, sapphire blue stat badges, emerald play buttons,
// warm gold highlights, and lush wooden boat and river valley tones.
// ============================================================================

// Top Circular Quick Action Buttons (Sound, Help, Close) - Warm Timber and Brass
val MenuQuickActionBgTop = Color(0xFF5A2A0C)
val MenuQuickActionBgBottom = Color(0xFF331605)
val MenuQuickActionBorder = Color(0xFFD97706)

// Stat Capsule Bar (Stars, Levels, Rank) - Warm Cedar Plank & Carved Groove
val MenuStatBarBgTop = Color(0xF04A260E)
val MenuStatBarBgBottom = Color(0xF02A1305)
val MenuStatBarBorder = Color(0xFFD97706)
val MenuStatDivider = Color(0x551E0C03)

// 1. Continue / Play Button (Deep Forest Moss & Carved Amber Timber)
val MenuBtnGreenTop = Color(0xFF3E8E41)
val MenuBtnGreenMid = Color(0xFF286A2B)
val MenuBtnGreenBottom = Color(0xFF1B4D24)
val MenuBtnGreenBorder = Color(0xFFD97706)

// 2. Levels Menu Button (Rich Nautical Cedar & Carved Timber Rim)
val MenuBtnBlueTop = Color(0xFF5C3314)
val MenuBtnBlueMid = Color(0xFF42220E)
val MenuBtnBlueBottom = Color(0xFF2E1508)
val MenuBtnBlueBorder = Color(0xFFB45309)

// 3. River Academy / Tutorial Button (Vibrant Golden Amber Timber)
val MenuBtnAmberTop = Color(0xFFD97706)
val MenuBtnAmberMid = Color(0xFFB45309)
val MenuBtnAmberBottom = Color(0xFF92400E)
val MenuBtnAmberBorder = Color(0xFFFDE68A)

// 4. High Score Leaderboard Button (Royal Mahogany Timber)
val MenuBtnPurpleTop = Color(0xFF4E2218)
val MenuBtnPurpleMid = Color(0xFF3B1810)
val MenuBtnPurpleBottom = Color(0xFF2A0F08)
val MenuBtnPurpleBorder = Color(0xFFD97706)

// 5. Settings Menu Button (Weathered Oak Timber)
val MenuBtnCyanTop = Color(0xFF462814)
val MenuBtnCyanMid = Color(0xFF361E0E)
val MenuBtnCyanBottom = Color(0xFF261308)
val MenuBtnCyanBorder = Color(0xFFA06634)

// 6. Quit Game Button (Deep Rust Timber)
val MenuBtnRedTop = Color(0xFF8B2515)
val MenuBtnRedMid = Color(0xFF6E1B0D)
val MenuBtnRedBottom = Color(0xFF4D1107)
val MenuBtnRedBorder = Color(0xFFB91C1C)

// Subtitle & Accent Colors - Warm Creams, Golds, and Champagne
val MenuTextWhite = Color(0xFFFFFBEB)
val MenuSubtextWhite = Color(0xFFFEF3C7)
val MenuSubtextMuted = Color(0xFFFDE68A)
val MenuCrownCyan = Color(0xFFFACC15)
val MenuStarGold = Color(0xFFFACC15)
val MenuTrophyGold = Color(0xFFFBBF24)

// Unified Rustic Wood Theme Tokens
val WoodCarvedBorder = Color(0xFFB45309)
val WoodGoldBorder = Color(0xFFD97706)
val WoodPanelBgTop = Color(0xF042220E)
val WoodPanelBgBottom = Color(0xF0281305)
val WoodCreamText = Color(0xFFFEF3C7)
val WoodGoldText = Color(0xFFFACC15)

// Translucent & Glassy Surface Tokens
val GlassWhitePristine = Color(0xFFFFFFFF)
val GlassWhiteSpecular = Color(0xF2FFFFFF)       // 95% opacity specular white
val GlassWhiteDominant = Color(0xD9FFFFFF)       // 85% opacity frosted white
val GlassWhiteElevated = Color(0xBEFFFFFF)       // 75% opacity soft frosted white
val GlassWhiteSubtle   = Color(0x8AFFFFFF)       // 54% opacity translucent white
val GlassWhiteUltra    = Color(0x38FFFFFF)       // 22% ultra-translucent highlight
val GlassWhiteBorder   = Color(0x99FFFFFF)       // 60% translucent border

// Canvas & Glass Card Backgrounds
val GlassCanvasBgTop    = Color(0xFFF8FAFC)      // Pure crystalline light gray/white
val GlassCanvasBgBottom = Color(0xFFE2E8F0)      // Smooth pearlescent gradient end
val GlassCardFill       = Color(0xCCFFFFFF)      // 80% glassy frosted card
val GlassCardInset      = Color(0xB3F1F5F9)      // 70% soft crystalline inset
val GlassCardBorder     = Color(0xCCFFFFFF)      // Crisp glass outline rim

// Neutral Contrast & Rich Typography (Deep Onyx & Silver/Slate)
val GlassTextPrimary   = Color(0xFF0F172A)       // High-contrast deep onyx slate
val GlassTextSecondary = Color(0xFF334155)       // Neutral medium charcoal
val GlassTextMuted     = Color(0xFF64748B)       // Refined slate gray
val GlassTextSubtle    = Color(0xFF94A3B8)       // Subtle hint slate

// Accent Jewels (Luminous Emerald, Amber Gold, Coral Ruby, Amethyst)
val GlassAccentGold       = Color(0xFFD97706)    // Warm golden amber for trophies & stars
val GlassAccentGoldLight  = Color(0xFFF59E0B)    // Sparkling amber highlight
val GlassAccentEmerald    = Color(0xFF059669)    // Crisp jade emerald
val GlassAccentRuby       = Color(0xFFE11D48)    // Clean ruby crimson
val GlassAccentAmethyst   = Color(0xFF7C3AED)    // Regal amethyst

// Backward-compatible Classy Obsidian & Slate definitions re-mapped to Glass Tokens
val ClassyObsidianDeep     = GlassCanvasBgTop
val ClassyObsidianSurface  = GlassCardFill
val ClassyObsidianElevated = GlassWhiteElevated
val ClassyObsidianCard     = GlassCardFill
val ClassyObsidianBorder   = GlassCardBorder

// Champagne Gold tokens re-mapped to Glass Accent Tokens
val ClassyGoldRadiant   = GlassTextPrimary
val ClassyGoldChampagne = GlassAccentGoldLight
val ClassyGoldPrimary   = GlassAccentGold
val ClassyGoldBurnished = Color(0xFFB45309)
val ClassyGoldDeep      = Color(0xFF92400E)
val ClassyGoldShadow    = Color(0x33D97706)

// Slates
val ClassySlateLight  = GlassTextPrimary
val ClassySlateMuted  = GlassTextSecondary
val ClassySlateSubtle = GlassTextMuted
val ClassySlateDark   = GlassCardInset

// Status Jewels
val ClassyJewelEmerald     = GlassAccentEmerald
val ClassyJewelEmeraldGlow = Color(0x33059669)
val ClassyJewelSapphire    = Color(0xFF0284C7)
val ClassyJewelRuby        = GlassAccentRuby
val ClassyJewelRubyGlow    = Color(0x33E11D48)
val ClassyJewelAmethyst    = GlassAccentAmethyst

// ============================================================================
// RIVER-THEMED COLOR PALETTE (Deep Blue, Earthy Brown, Forest Green, Sunlit Gold)
// ============================================================================

// Deep River Blues (River currents, deep rapids, twilight waters)
val RiverDeepBlueDark   = Color(0xFF07192F)      // Midnight river depth & canvas backdrop
val RiverDeepBlueMid    = Color(0xFF0A2B4E)      // Deep flowing current / card surface
val RiverDeepBlueLight  = Color(0xFF0284C7)      // Sunlit flowing water / primary river blue
val RiverWaterCyan      = Color(0xFF38BDF8)      // White-water rapid crest & bright cyan rim
val RiverWaterFoam      = Color(0xFFE0F2FE)      // River foam highlight & readable mist text

// Earthy Browns (Riverbanks, wooden raft, timber signs, carved docks)
val RiverEarthBrownDark  = Color(0xFF2E190E)     // Deep carved timber shadow
val RiverEarthBrownMid   = Color(0xFF5A361D)     // Raft wood & rich shoreline loam
val RiverEarthBrownLight = Color(0xFF8B5A2B)     // Warm timber plank & dock post
val RiverTimberBorder    = Color(0xFFA06634)     // Carved wooden border rim
val RiverSandTan         = Color(0xFFD4A373)     // Sandy bank shore & warm text

// Forest Greens (Lush riverbank vegetation, mossy boulders, pine forests)
val RiverForestGreenDark  = Color(0xFF143B28)    // Pine grove shadow & badge container
val RiverForestGreenMid   = Color(0xFF22724A)    // Lush riverbank grass & play button
val RiverForestGreenLight = Color(0xFF2E8B57)    // Sea green moss & foliage highlight
val RiverMeadowGrass      = Color(0xFF4ADE80)    // Fresh meadow sprout & success tag
val RiverMossGreen        = Color(0xFF52B788)    // Riverbank moss & accent jewel

// River Sunlit Amber / Gold (Sunlight glistening on water, victory stars, compass, badges)
val RiverSunlitGold  = Color(0xFFF59E0B)         // Sun-drenched river ripple
val RiverGoldGlow    = Color(0xFFFACC15)         // Brilliant 24k gold for stars & trophies
val RiverGoldLight   = Color(0xFFFEF08A)         // Champagne gold crest highlight
val RiverGoldShadow  = Color(0x66D97706)

// Backward-compatible Theme Tokens mapped to Main Menu Casual Game Palette
val WoodSignboardBg          = Color(0xF04A260E)      // Warm cedar/mahogany card
val WoodCardBg               = Color(0xF03A1C08)      // Rich timber card surface
val WoodSignboardLight       = Color(0xFF8B5A2B)      // Warm timber plank
val WoodSignboardDark        = Color(0xFF2E1507)      // Deep carved mahogany
val WoodSignboardBorder      = Color(0xFFD97706)      // Warm golden amber border rim
val WoodInsetPanel           = Color(0xDD220F05)      // Deep carved mahogany inset
val WoodButtonTop            = Color(0xFF4ADE80)      // Vibrant emerald top (matches Play Button)
val WoodButtonBottom         = Color(0xFF22C55E)      // Lush emerald bottom
val WoodButtonBorder         = Color(0xFF86EFAC)      // Emerald highlight rim
val WoodGoldenText           = Color(0xFFFFFFFF)      // High-contrast clean white text
val WoodPillBackground       = Color(0xDD3A1C08)      // Warm timber pill container
val GoldenBankGlow           = Color(0xFFFACC15)      // Brilliant gold for stars and trophies
val GoldenBankGlowContainer  = Color(0x33FACC15)
val WoodScreenBg             = Color(0xFF07192F)
val WoodScreenBgTop          = Color(0xFF0F325E)
val WoodSurfaceCard          = Color(0xF03A1C08)      // Warm timber card
val WoodSurfaceCardBorder    = Color(0xFFD97706)      // Warm golden amber
val WoodTextMuted            = Color(0xFFFEF3C7)      // Warm cream / champagne
val WoodTextSubtle           = Color(0xFFFDE68A)      // Warm gold highlight
val WoodGoldAccent           = Color(0xFFFACC15)

// Vibrant Palette mapped to River Universe
val VibrantBackground       = Color(0xFF07192F)      // Deep atmospheric river twilight
val VibrantSurface          = Color(0xF03A1C08)
val VibrantSurfaceVariant   = Color(0xDD220F05)
val VibrantSurfaceBorder    = Color(0xFFD97706)

val VibrantTextPrimary      = Color(0xFFFFFFFF)
val VibrantTextSecondary    = Color(0xFFFEF3C7)
val VibrantTextTertiary     = Color(0xFFFDE68A)

val VibrantPrimary          = Color(0xFF22724A)      // Forest green primary
val VibrantPrimaryHover     = Color(0xFF2E8B57)
val VibrantPrimaryContainer = Color(0xFF143B28)
val VibrantOnPrimaryContainer = Color(0xFF86EFAC)

// River Scene Colors
val VibrantRiverCanvasFrame    = Color(0xFF38BDF8)
val VibrantRiverBankGrass      = Color(0xFF1B5E38)
val VibrantRiverBankBorder     = Color(0xFF143B28)
val VibrantWaterGradientTop    = Color(0xFF38BDF8)
val VibrantWaterGradientBottom = Color(0xFF0284C7)
val VibrantBoatWood            = Color(0xFF5A361D)      // Rich earthy timber
val VibrantBoatTrim            = Color(0xFFFACC15)

// Status & Accents
val VibrantSuccessGreen     = Color(0xFF22C55E)
val VibrantSuccessContainer = Color(0x3322C55E)
val VibrantWarningAmber     = Color(0xFFF59E0B)
val VibrantWarningContainer = Color(0x33F59E0B)
val VibrantErrorRed         = Color(0xFFEF4444)
val VibrantErrorContainer   = Color(0x33EF4444)
val VibrantGold             = Color(0xFFFACC15)
val VibrantSecondary        = Color(0xFF0284C7)      // River blue
val VibrantAccent           = Color(0xFF38BDF8)

// Dark Theme Variants mapped gracefully
val DarkVibrantBackground       = Color(0xFF05182F)
val DarkVibrantSurface          = Color(0xF2071E38)
val DarkVibrantSurfaceVariant   = Color(0xCC031222)
val DarkVibrantPrimary          = Color(0xFF22724A)
val DarkVibrantPrimaryContainer = Color(0xFF143B28)
val DarkVibrantTextPrimary      = Color(0xFFFFFFFF)
val DarkVibrantTextSecondary    = Color(0xFFE0F2FE)
