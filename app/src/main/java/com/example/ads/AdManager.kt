package com.example.ads

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Google Mobile Ads manager handling official AdMob test ads for development.
 * Official Google Test Ad Unit IDs:
 * - App ID: ca-app-pub-3940256099942544~3347511713
 * - Banner: ca-app-pub-3940256099942544/6300978111
 * - Interstitial: ca-app-pub-3940256099942544/1033173712
 * - Rewarded: ca-app-pub-3940256099942544/5224354917
 */
object AdManager {
    private const val TAG = "AdManager"

    // Official Google AdMob sample test ad unit IDs
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var isInitialized = false
    private var isRewardedLoading = false

    private val _isRewardedAdLoaded = MutableStateFlow(false)
    val isRewardedAdLoaded: StateFlow<Boolean> = _isRewardedAdLoaded.asStateFlow()

    private val _isInterstitialAdLoaded = MutableStateFlow(false)
    val isInterstitialAdLoaded: StateFlow<Boolean> = _isInterstitialAdLoaded.asStateFlow()

    /**
     * Checks if the device has an active internet connection.
     */
    fun isNetworkAvailable(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false
            val activeNetwork = cm.activeNetwork ?: return false
            val capabilities = cm.getNetworkCapabilities(activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking network connectivity", e)
            false
        }
    }

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) { status ->
                Log.d(TAG, "Google Mobile Ads initialized successfully: $status")
                isInitialized = true
                loadInterstitialAd(context)
                loadRewardedAd(context)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MobileAds", e)
        }
    }

    fun loadInterstitialAd(context: Context) {
        if (!isNetworkAvailable(context)) {
            _isInterstitialAdLoaded.value = false
            return
        }
        try {
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                context,
                INTERSTITIAL_AD_UNIT_ID,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        Log.d(TAG, "AdMob test interstitial ad loaded successfully.")
                        interstitialAd = ad
                        _isInterstitialAdLoaded.value = true
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "AdMob test interstitial failed to load: ${loadAdError.message}")
                        interstitialAd = null
                        _isInterstitialAdLoaded.value = false
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception loading interstitial ad", e)
            _isInterstitialAdLoaded.value = false
        }
    }

    fun showInterstitialAd(activity: Activity, onAdClosed: () -> Unit) {
        val ad = interstitialAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad dismissed.")
                    interstitialAd = null
                    _isInterstitialAdLoaded.value = false
                    loadInterstitialAd(activity)
                    onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.w(TAG, "Interstitial ad failed to show: ${adError.message}")
                    interstitialAd = null
                    _isInterstitialAdLoaded.value = false
                    loadInterstitialAd(activity)
                    onAdClosed()
                }
            }
            ad.show(activity)
        } else {
            Log.d(TAG, "Interstitial ad not ready, executing callback and loading for next time.")
            loadInterstitialAd(activity)
            onAdClosed()
        }
    }

    fun loadRewardedAd(context: Context) {
        if (isRewardedLoading || rewardedAd != null) return
        if (!isNetworkAvailable(context)) {
            Log.w(TAG, "Cannot preload rewarded ad: No internet connection.")
            _isRewardedAdLoaded.value = false
            return
        }
        isRewardedLoading = true
        try {
            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(
                context,
                REWARDED_AD_UNIT_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(TAG, "AdMob test rewarded ad loaded successfully.")
                        rewardedAd = ad
                        isRewardedLoading = false
                        _isRewardedAdLoaded.value = true
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "AdMob test rewarded ad failed to load: ${loadAdError.message}")
                        rewardedAd = null
                        isRewardedLoading = false
                        _isRewardedAdLoaded.value = false
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception loading rewarded ad", e)
            isRewardedLoading = false
            _isRewardedAdLoaded.value = false
        }
    }

    /**
     * Loads and displays a Google Test Rewarded Ad.
     * Enforces strict rules:
     * 1. Requires an active network connection. Fails without unlocking if offline.
     * 2. Loads and plays the official Google Test Rewarded Ad.
     * 3. Reward is ONLY granted if the user completes watching the ad.
     * 4. If the ad is closed before completion, fails to load, or fails to play, NO reward is granted.
     */
    fun loadAndShowRewardedAd(
        activity: Activity,
        onLoading: () -> Unit = {},
        onRewardEarned: () -> Unit,
        onAdFailed: (errorMessage: String) -> Unit,
        onAdDismissed: (() -> Unit)? = null
    ) {
        // Step 1: Network Connectivity Check
        if (!isNetworkAvailable(activity)) {
            Log.w(TAG, "No internet connection detected for rewarded ad.")
            onAdFailed("No internet connection detected. Please connect to Wi-Fi or mobile data to watch an ad and unlock this level.")
            return
        }

        // Step 2: Use preloaded ad if available
        val readyAd = rewardedAd
        if (readyAd != null) {
            rewardedAd = null
            _isRewardedAdLoaded.value = false
            displayRewardedAd(
                activity = activity,
                ad = readyAd,
                onRewardEarned = onRewardEarned,
                onAdFailed = onAdFailed,
                onAdDismissed = onAdDismissed
            )
            return
        }

        // Step 3: Load on-demand
        onLoading()
        isRewardedLoading = true
        try {
            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(
                activity,
                REWARDED_AD_UNIT_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d(TAG, "AdMob test rewarded ad loaded on-demand successfully.")
                        isRewardedLoading = false
                        displayRewardedAd(
                            activity = activity,
                            ad = ad,
                            onRewardEarned = onRewardEarned,
                            onAdFailed = onAdFailed,
                            onAdDismissed = onAdDismissed
                        )
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "AdMob test rewarded ad failed to load on-demand: ${loadAdError.message}")
                        isRewardedLoading = false
                        rewardedAd = null
                        _isRewardedAdLoaded.value = false
                        onAdFailed("Failed to load test ad (${loadAdError.code}: ${loadAdError.message}). Please check your connection and try again.")
                        onAdDismissed?.invoke()
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception loading on-demand rewarded ad", e)
            isRewardedLoading = false
            _isRewardedAdLoaded.value = false
            onAdFailed("Error requesting ad: ${e.localizedMessage ?: "Unknown error"}. No level was unlocked.")
            onAdDismissed?.invoke()
        }
    }

    private fun displayRewardedAd(
        activity: Activity,
        ad: RewardedAd,
        onRewardEarned: () -> Unit,
        onAdFailed: (errorMessage: String) -> Unit,
        onAdDismissed: (() -> Unit)? = null
    ) {
        var userEarnedReward = false

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Rewarded ad dismissed. userEarnedReward=$userEarnedReward")
                // Preload next ad for future use
                loadRewardedAd(activity)
                if (userEarnedReward) {
                    onRewardEarned()
                } else {
                    onAdFailed("The ad was closed before completion. You must watch the complete ad to unlock the level.")
                }
                onAdDismissed?.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.w(TAG, "Rewarded ad failed to show: ${adError.message}")
                loadRewardedAd(activity)
                onAdFailed("Ad failed to play: ${adError.message}. No level was unlocked.")
                onAdDismissed?.invoke()
            }
        }

        ad.show(activity) { rewardItem ->
            Log.d(TAG, "User completed watching rewarded ad and earned reward: ${rewardItem.amount} ${rewardItem.type}")
            userEarnedReward = true
        }
    }
}
