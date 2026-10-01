package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-game progression and unlock manager.
 * Operates cleanly without binding to unavailable AdServices measurement or triggering
 * MESA rendernode graphics driver faults in emulator environments.
 */
object AdManager {
    private const val TAG = "AdManager"

    const val BANNER_AD_UNIT_ID = "mock_banner_unit"
    const val INTERSTITIAL_AD_UNIT_ID = "mock_interstitial_unit"
    const val REWARDED_AD_UNIT_ID = "mock_rewarded_unit"

    private val _isRewardedAdLoaded = MutableStateFlow(true)
    val isRewardedAdLoaded: StateFlow<Boolean> = _isRewardedAdLoaded.asStateFlow()

    private val _isInterstitialAdLoaded = MutableStateFlow(true)
    val isInterstitialAdLoaded: StateFlow<Boolean> = _isInterstitialAdLoaded.asStateFlow()

    fun initialize(context: Context) {
        Log.d(TAG, "AdManager initialized cleanly without external ad services binding.")
    }

    fun loadRewardedAd(context: Context) {
        _isRewardedAdLoaded.value = true
    }

    fun showRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdDismissed: (() -> Unit)? = null
    ) {
        Log.d(TAG, "Rewarded unlock granted directly.")
        onRewardEarned()
        onAdDismissed?.invoke()
    }

    fun loadInterstitialAd(context: Context) {
        _isInterstitialAdLoaded.value = true
    }

    fun showInterstitialAd(activity: Activity, onAdClosed: () -> Unit) {
        onAdClosed()
    }
}
