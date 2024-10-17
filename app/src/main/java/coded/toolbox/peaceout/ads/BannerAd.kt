package coded.toolbox.peaceout.ads

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds

@Composable
fun BannerAd(adSize: AdSize, adUnitId: String) {
    val context = LocalContext.current
    var adView by remember { mutableStateOf<AdView?>(null) }
    val tag = "BannerAd"

    LaunchedEffect(key1 = Unit) {
        MobileAds.initialize(context) {}
    }

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            AdView(it).apply {
                setAdSize(adSize)
                this.adUnitId = adUnitId
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        Log.d(tag, "Ad loaded.")
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.d(tag, "Ad failed to load: ${adError.message}")
                    }

                    override fun onAdOpened() {
                        Log.d(tag, "Ad opened.")
                    }

                    override fun onAdClicked() {
                        Log.d(tag, "Ad clicked.")
                    }

                    override fun onAdClosed() {
                        Log.d(tag, "Ad closed.")
                    }

                    override fun onAdImpression() {
                        Log.d(tag, "Ad impression recorded.")
                    }
                }
                loadAd(AdRequest.Builder().build())
                adView = this
            }
        }
    )

    DisposableEffect(key1 = adView) {
        onDispose {
            adView?.destroy()
        }
    }
}