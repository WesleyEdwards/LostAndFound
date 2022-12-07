package com.example.lostandfound.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.*

@SuppressLint("MissingPermission")
@Composable
fun LAFAd() {
    val isInEditMode = LocalInspectionMode.current
    MobileAds.initialize(LocalContext.current) {}

    if (isInEditMode) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Red)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = White,
            text = "Advert Here",
        )
    } else {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-5839863331906214/1943777729"
                    loadAd(AdRequest.Builder().build())
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            println("Ad loaded")
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            println("Ad failed to load")
                            println(p0)
                        }
                    }
                }
            }
        )
    }
}