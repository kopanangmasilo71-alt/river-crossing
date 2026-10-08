package com.example.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ads.AdManager
import com.example.ui.theme.GoldenBankGlow
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

private const val TAG = "BannerAdView"

/**
 * Standard AdMob Banner Ad component for Jetpack Compose.
 * Displays official Google Test Banner Ad unit (BANNER_AD_UNIT_ID) inside a polished container.
 * Features:
 * - Proper lifecycle cleanup via DisposableEffect.
 * - Graceful fallback badge when offline or during initial loading.
 * - TestTag for UI test automation.
 */
@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adUnitId: String = AdManager.BANNER_AD_UNIT_ID
) {
    val context = LocalContext.current
    var isAdLoaded by remember { mutableStateOf(false) }
    var adLoadFailed by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0x990A1D37),
        border = BorderStroke(1.2.dp, Color(0x6638BDF8)),
        shadowElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .testTag("banner_ad_container")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 6.dp)
        ) {
            // Subtle "SPONSORED / AD" top label badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0x660284C7),
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    Text(
                        text = "ADVERTISEMENT",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBAE6FD),
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                }

                Text(
                    text = "Free to Play Supported",
                    fontSize = 8.5.sp,
                    color = Color(0x88BAE6FD),
                    letterSpacing = 0.4.sp
                )
            }

            // Banner Host Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x33000000)),
                contentAlignment = Alignment.Center
            ) {
                // Actual AdMob AdView
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admob_banner_view"),
                    factory = { ctx: Context ->
                        AdView(ctx).apply {
                            setAdSize(AdSize.BANNER)
                            this.adUnitId = adUnitId
                            adListener = object : AdListener() {
                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                    Log.d(TAG, "AdMob banner ad loaded successfully.")
                                    isAdLoaded = true
                                    adLoadFailed = false
                                }

                                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                    super.onAdFailedToLoad(loadAdError)
                                    Log.w(TAG, "AdMob banner ad failed to load: ${loadAdError.message}")
                                    isAdLoaded = false
                                    adLoadFailed = true
                                }
                            }
                            try {
                                val adRequest = AdRequest.Builder().build()
                                loadAd(adRequest)
                            } catch (e: Exception) {
                                Log.e(TAG, "Error initiating banner ad request", e)
                                adLoadFailed = true
                            }
                        }
                    },
                    update = {
                        // View is already loaded
                    }
                )

                // Placeholder / Loading / Fallback display when ad is loading or offline
                if (!isAdLoaded) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF1E3A8A),
                            border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "AdMob",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenBankGlow,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = if (adLoadFailed) "Google Test Banner Ad • Ready" else "Loading Google Mobile Ad...",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF93C5FD)
                        )
                    }
                }
            }
        }
    }
}
