package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.udacity.abhishek.androiddisplayjokelib.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity implements ListenerClass {

    private String joke;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOfInterstitialAd();
    }

    private void setupOfInterstitialAd() {
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, getString(R.string.adMobAppId));

        // Create Interstitial Ad and set the adUnitId.
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitialAdUnitId));
        reqOfNewInterstitialAd();

        // Show Interstitial Ad when user click button
        Button button = findViewById(R.id.button_tell_joke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskClass(MainActivity.this).execute();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("MainActivity", "The interstitial Ad wasn't loaded yet.");
                    if (!TextUtils.isEmpty(joke)) {
                        launchOfJokeAct();
                    }
                }
            }
        });

        // launch joke activity when interstitial ad closed
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                reqOfNewInterstitialAd();
                if (!TextUtils.isEmpty(joke)) {
                    launchOfJokeAct();
                }
            }
        });
    }

    private void launchOfJokeAct() {
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE_CODE, joke);
        startActivity(intent);
    }

    private void reqOfNewInterstitialAd() {
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            interstitialAd.loadAd(adRequest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void jokeLoaded(String joke) {
        Log.d("onJokeLoaded", "Joke is: " + joke);
        this.joke = joke;
    }
}
