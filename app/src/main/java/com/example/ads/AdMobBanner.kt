package com.example.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Clean placeholder that eliminates Chromium WebView instantiation,
 * completely preventing MESA rendernode errors and AdServices measurement faults.
 */
@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = ""
) {
    // No-op to prevent headless emulator MESA rendernode and adservices errors
}
