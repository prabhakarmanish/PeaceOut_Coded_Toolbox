package coded.toolbox.peaceout.ads

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coded.toolbox.peaceout.MainActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun InterstitialAd() {
    val context = LocalContext.current
    var mInterstitialAd by remember { mutableStateOf<InterstitialAd?>(null) }
    var isAdLoaded by remember { mutableStateOf(false) }
    val TAG = "InterstitialAdScreen"

    // Initialize Mobile Ads SDK
    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
    }

    // Function to load Interstitial Ad
    fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712", // Replace with your actual ad unit ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mInterstitialAd = null
                    isAdLoaded = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    isAdLoaded = true

                    // Set the FullScreenContentCallback with all callbacks
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                Log.d(TAG, "Ad was clicked.")
                            }

                            override fun onAdDismissedFullScreenContent() {
                                Log.d(TAG, "Ad was dismissed.")
                                mInterstitialAd = null
                                isAdLoaded = false
                                loadInterstitialAd() // Reload the ad after dismissal
                                //Toast.makeText(context,"Ad Closed",Toast.LENGTH_SHORT).show()
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.d(TAG, "Ad failed to show.")
                                mInterstitialAd = null
                                isAdLoaded = false
                            }

                            override fun onAdImpression() {
                                Log.d(TAG, "Ad recorded an impression.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad showed fullscreen content.")
                            }
                        }
                }
            }
        )
    }

    // Load the Interstitial Ad when composable starts
    LaunchedEffect(Unit) {
        loadInterstitialAd()
    }

    // Button to show the Interstitial Ad
    Button(onClick = {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as MainActivity)
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
            loadInterstitialAd() // Optionally reload the ad if it wasn't ready
        }
    }) {
        Text(text = if (isAdLoaded) "Show Interstitial Ad" else "Loading Ad...")
    }
}