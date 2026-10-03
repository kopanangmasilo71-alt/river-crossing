package com.example.ads

import android.app.Activity
import android.content.Context
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

    private val _isRewardedAdLoaded = MutableStateFlow(false)
    val isRewardedAdLoaded: StateFlow<Boolean> = _isRewardedAdLoaded.asStateFlow()

    private val _isInterstitialAdLoaded = MutableStateFlow(false)
    val isInterstitialAdLoaded: StateFlow<Boolean> = _isInterstitialAdLoaded.asStateFlow()

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
                        _isRewardedAdLoaded.value = true
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "AdMob test rewarded ad failed to load: ${loadAdError.message}")
                        rewardedAd = null
                        _isRewardedAdLoaded.value = false
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception loading rewarded ad", e)
            _isRewardedAdLoaded.value = false
        }
    }

    fun showRewardedAd(
        activity: Activity,
        onRewardEarned: () -> Unit,
        onAdDismissed: (() -> Unit)? = null
    ) {
        val ad = rewardedAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad dismissed.")
                    rewardedAd = null
                    _isRewardedAdLoaded.value = false
                    loadRewardedAd(activity)
                    onAdDismissed?.invoke()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.w(TAG, "Rewarded ad failed to show: ${adError.message}")
                    rewardedAd = null
                    _isRewardedAdLoaded.value = false
                    loadRewardedAd(activity)
                    onRewardEarned()
                    onAdDismissed?.invoke()
                }
            }
            ad.show(activity) { rewardItem ->
                Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
                onRewardEarned()
            }
        } else {
            Log.d(TAG, "Rewarded ad not loaded yet, granting reward gracefully.")
            loadRewardedAd(activity)
            onRewardEarned()
            onAdDismissed?.invoke()
        }
    }
}
